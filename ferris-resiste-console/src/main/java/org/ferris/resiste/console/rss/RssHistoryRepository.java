package org.ferris.resiste.console.rss;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.ferris.resiste.console.sql.SqlConnection;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RssHistoryRepository {

    private static final long serialVersionUID = 7491906484654964631L;

    @Inject
    protected Logger log;

    @Inject
    protected SqlConnection conn;

    public Optional<RssHistory> find(String feedId, String entryId) {
        
        log.info(String.format("Find RSS entry history feedId=\"%s\", entryId=\"%s\"", feedId, entryId));
        
        Optional<RssHistory> retval
            = Optional.empty();

        StringBuilder sp = new StringBuilder();
        sp.append(" select ");
        sp.append("     feed_id, entry_id, published_on, last_found_on ");
        sp.append(" from ");
        sp.append("     rss_entry_history ");
        sp.append(" where ");
        sp.append("     feed_id=? ");
        sp.append("     and ");
        sp.append("     entry_id=? ");
        sp.append(" for update of ");
        sp.append("     last_found_on ");

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareUpdatableStatement(sp.toString());
            
            stmt.setString(1, feedId);
            stmt.setString(2, entryId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                retval = Optional.of(
                    new RssHistory(feedId, feedId, rs.getTimestamp("published_on").toInstant())
                );
                
                rs.updateDate(4, Date.valueOf(LocalDate.now()));
                rs.updateRow();
            }

        } catch (Throwable t) {
            throw new RuntimeException(
                String.format("Problem finding feed entry in history table feedId=\"%s\", entryId=\"%s\", sql=\"%s\""
                    , feedId, entryId, sp.toString()
                ), t
            );
        } finally {
            conn.close(stmt, rs);
        }

        return retval;
    }


    protected void store(RssHistory h) {
        
        log.info(String.format("Store RSS entry history %s", h));

        StringBuilder sp = new StringBuilder();
        sp.append(" insert into rss_entry_history ");
        sp.append("     (feed_id, entry_id, published_on, last_found_on) ");
        sp.append(" values ");
        sp.append("     (?, ?, ?, ?) ");

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sp.toString());

            // FeedId
            if (h.getFeedId().length() > 200) {
                throw new IllegalArgumentException(
                    String.format(
                        "feed_id column can be only 200 of the %d characters of \"%s\"", h.getFeedId().length(), h.getFeedId()
                    )
                );
            }
            stmt.setString(1, h.getFeedId());

            // EntryId
            if (h.getEntryId().length() > 300) {
                throw new IllegalArgumentException(
                    String.format(
                        "entry_id column can be only 300 of the %d characters of \"%s\"", h.getEntryId().length(), h.getEntryId()
                    )
                );
            }
            stmt.setString(2, h.getEntryId());

            // PublishedOn
            stmt.setTimestamp(3, Timestamp.from(h.getPublished()));
            
            // LastFoundOn
            stmt.setDate(4, Date.valueOf(LocalDate.now()));

            // Execute
            int changed = stmt.executeUpdate();
            if (changed != 1) {
                throw new RuntimeException(
                    String.format(
                        "The executeUpdate() into the rss_entry_history table did not return exactly 1 row: %d"
                        , changed)
                );
            }
        } catch (Throwable t) {
            throw new RuntimeException(
                String.format("Problem inserting in history table %s:"
                    , String.valueOf(h)
                ), t
            );
        } finally {
            conn.close(stmt, rs);
        }
    }
    
    
    public void cleanup() {
        
        log.info("Cleanup RSS entry history");
        
        StringBuilder sp = new StringBuilder();
        sp.append(" delete from ");
        sp.append("     rss_entry_history ");
        sp.append(" where ");
        sp.append("     last_found_on < ? ");

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sp.toString());
            // 6 months in the past
            LocalDate ld = LocalDate.now().plusMonths(-6); 
            log.info(String.format("Deleted RSS entry history older than %s", ld.toString()));
            stmt.setDate(1, Date.valueOf(ld));
            int deletedRows = stmt.executeUpdate();
            log.info(String.format("Deleted %d rows from RSS entry history", deletedRows));
        } catch (Throwable t) {
            throw new RuntimeException(
                "Problem cleaning up the RSS entry history table.", t
            );
        } finally {
            conn.close(stmt, rs);
        }
    }


}
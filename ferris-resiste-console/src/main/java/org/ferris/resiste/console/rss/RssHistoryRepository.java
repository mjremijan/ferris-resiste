package org.ferris.resiste.console.rss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Optional;
import javax.inject.Inject;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.sql.SqlTool;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssHistoryRepository {

    private static final long serialVersionUID = 7491906484654964631L;

    @Inject
    protected Logger log;

    @Inject
    protected SqlTool sqlTool;

    protected Connection conn;

    @Inject
    public RssHistoryRepository(DataDirectory root) {

        String url = "";
        try {
            url = String.format(
                "jdbc:derby:%s/resiste"
              , root.getCanonicalPath()
            );
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format(
                        "Problem creating database connection url to directory \"%s\""
                      , String.valueOf(root)
                  )
                , e
            );
        }

        String user = "sa";
        String pass = "Hg1@x$6fhX938XS";

        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem connecting to database \"%s\", \"%s\", \"%s\""
                    , url, user, pass),
                 e
            );
        }

        try {
            conn.setSchema("APP");
        } catch (Exception e) {
            throw new RuntimeException(
                "Problem setting database schema to \"APP\"", e
            );
        }
    }

    public Optional<RssHistory> find(String feedId, String entryId) {
        Optional<RssHistory> retval
            = Optional.empty();

        StringBuilder sp = new StringBuilder();
        sp.append(" select ");
        sp.append("     feed_id, entry_id, published_on ");
        sp.append(" from ");
        sp.append("     rss_entry_history ");
        sp.append(" where ");
        sp.append("     feed_id=? ");
        sp.append("     and ");
        sp.append("     entry_id=? ");

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sp.toString());
            stmt.setString(1, feedId);
            stmt.setString(2, entryId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                retval =Optional.of(
                    new RssHistory(feedId, feedId, rs.getTimestamp("published_on").toInstant())
                );
            }

        } catch (Throwable t) {
            throw new RuntimeException(
                String.format("Problem finding feed entry in history table feedId=\"%s\", entryId=\"%s\", sql=\"%s\""
                    , feedId, entryId, sp.toString()
                ), t
            );
        } finally {
            sqlTool.close(stmt, rs);
        }

        return retval;
    }


    protected void store(RssHistory h) {
        log.info(String.format("Store feed entry in history %s", h));

        StringBuilder sp = new StringBuilder();
        sp.append(" insert into rss_entry_history ");
        sp.append("     (feed_id, entry_id, published_on) ");
        sp.append(" values ");
        sp.append("     (?, ?, ?) ");

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
            if (h.getEntryId().length() > 200) {
                throw new IllegalArgumentException(
                    String.format(
                        "entry_id column can be only 200 of the %d characters of \"%s\"", h.getEntryId().length(), h.getEntryId()
                    )
                );
            }
            stmt.setString(2, h.getEntryId());

            // PublishedOn
            stmt.setTimestamp(3, Timestamp.from(h.getPublished()));

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
            sqlTool.close(stmt, rs);
        }
    }


}
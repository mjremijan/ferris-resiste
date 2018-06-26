package org.ferris.resiste.db.flatfiletoderby;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FlatFileToDerbyIT {

    List<RssHistory> history;

    Connection conn;

    PreparedStatement stmt;

    @Test
    public void loadDerbyDatabaseWithFlatFileData() throws Exception {
        System.out.printf("WELCOME%n");
        loadHistoryFromFile();
        connect();
        prepare();
        begin();
        insert();
        commit();
        System.out.printf("DONE%n");
    }

    protected void begin() throws Exception {
        conn.setAutoCommit(false);
    }

    protected void commit() throws Exception {
        conn.commit();
    }

    public void prepare() throws Exception {
        conn.setSchema("APP");
        StringBuilder sp = new StringBuilder();
        sp.append(" insert into rss_entry_history ");
        sp.append("     (feed_id, entry_id, published_on) ");
        sp.append(" values ");
        sp.append("     (?, ?, ?) ");
        stmt = conn.prepareStatement(sp.toString());
    }

    protected void insert() {
        history.forEach(h -> {
            try {
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
                System.out.printf("Inserting %s%n", h.getEntryId());
                int changed = stmt.executeUpdate();

            } catch (SQLException t) {
                throw new RuntimeException(t);
            }
        });
    }

    public void connect() throws Exception {
        conn = DriverManager.getConnection(
            "jdbc:derby://localhost:1527/resiste;",
            "resiste_standalone",
            "x4A03HZ7ZV*lzB%");
    }

    public void loadHistoryFromFile() throws Exception {
        history = new LinkedList<>();
        Scanner input
            = new Scanner(new File("C:\\Users\\Michael\\Downloads\\rss_history.dat"));
        while (input.hasNext()) {

            String next = input.nextLine();

            if (next.isEmpty()) {
                continue;
            }

            String[] tokens = next.split("\\t");
            if (tokens.length != 3) {
                throw new Exception(
                    String.format("Line does not have 3 tokens \"%s\"", next)
                );
            }

            RssHistory h = new RssHistory(
                tokens[0] // feedId
                ,
                 tokens[1] // entryId
                ,
                 Instant.parse(tokens[2]) // publishedDate
            );

            history.add(h);
        }
    }
}

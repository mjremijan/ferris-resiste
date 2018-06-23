--
-- Create the rss_entry_history table
-- 
-- RSS id values are all over the place so it's hard to know an exact
-- size that will work.  This is 200 characters.  This should be big enough
-- ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|ooooooooo|
--
create table rss_entry_history (
      feed_id         varchar(200) not null
    , entry_id        varchar(200) not null
    , published_on    timestamp not null
    , primary key (feed_id, entry_id)
);

-- Permissions
grant select, insert, update on rss_entry_history to resiste_standalone;

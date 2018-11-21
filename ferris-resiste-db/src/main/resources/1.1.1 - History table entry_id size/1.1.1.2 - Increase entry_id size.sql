--
-- Increase the size of rss_entry_history.entry_id
-- 
-- In production, an RSS feed was added which had an entry_id vallue
-- greater than 200 characters....uggh :(
--
-- Update entry_id to 300 characters.
--

-- Create new column which is bigger
alter table rss_entry_history add column entry_id_2 varchar(300);

-- Copy old column into new column
update rss_entry_history set entry_id_2 = entry_id;

-- Drop old column
alter table rss_entry_history drop entry_id;

-- Rename new column
rename column rss_entry_history.entry_id_2 to entry_id;

-- Add not null constraint to column
alter table rss_entry_history alter column entry_id not null;

-- Recreate primary key
alter table rss_entry_history add primary key (feed_id, entry_id);

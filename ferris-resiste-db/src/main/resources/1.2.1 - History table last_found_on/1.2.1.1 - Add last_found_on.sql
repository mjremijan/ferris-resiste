--
-- Add a new field rss_entry_history.last_found_on
-- 
--

-- Create new column which is bigger
alter table rss_entry_history add column last_found_on date;

-- Set the value of the new column to now
update rss_entry_history set last_found_on = CURRENT_DATE;

-- Add not null constraint to column
alter table rss_entry_history alter column last_found_on not null;

-- Recreate index
create index lastfoundonindex ON rss_entry_history(last_found_on);


# ferris-resiste-console

A **RE**ally **SI**mple **S**yndication **T**o **E**mail system

This is a Java stand-alone RSS to email client.  The binary of this project
can be downloaded, unziped, configured, and run with no additional server setup
needed (it even comes with it's own JRE so not even Java needs to be installed
on the server). 

See the project's documentation for more details.

# Production Deployment Procedures

1. Commit all files.
1. Run parent "Update Versions", set production version
1. Commit all POM files.
1. Create TAG for the next version.
1. Run parent "Update Versions" to update all POM files to the next SNAPSHOT
1. Commit all POM files.
1. Push all (including TAG) to GitHub
1. Checkout the TAG
1. Run parent "Clean and Verify"
1. Run parent "Site"
1. Run this "Assembly"
1. Run parent "Site Deploy" 
1. Create GitHub release
1. Upload this *.zip and *.tar.gz files to the GitHub release



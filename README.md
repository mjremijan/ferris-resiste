# ferris-resiste

A **RE**ally **SI**mple **S**yndication **T**o **E**mail system

RSS to email system

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
1. Run -console "Assembly"
1. Run parent "Site Deploy" 
1. Create GitHub release
1. Upload this *.zip and *.tar.gz files to the GitHub release

# scala-utilities
Collection of utility code shared amongst scala libraries

## Publishing

### Publishing Local for test
Any branch: `sbt publishLocal`
Build and deploy the jar to your local maven repository

### Publish Snapshot
Any branch (but usually should be master): `sbt publish`
This will build the jar as a snapshot and push it to blackfynn maven snapshots

### Release
Master branch: `sbt release`
Follow the prompts to update the version and build the jar as an official release and push it to blackfynn maven releases

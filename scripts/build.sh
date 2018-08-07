#!/bin/bash

set +x

COPR=${1}
PACKAGE=${2}
ORG=${3}
PROJECT=${4}

whoami

echo "${1} -- ${2} -- ${3} -- ${4}"
LAST_PACKAGE_BUILD=$(copr get-package --name ${PACKAGE} \
  --with-latest-succeeded-build ${COPR} | \
  python -c 'import sys, json; print json.load(sys.stdin)["latest_succeeded_build"]["submitted_on"]' 2>/dev/null \
)

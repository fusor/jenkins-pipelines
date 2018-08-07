#!groovy
pipeline {
  agent any

  def copr = "@ansible-service-broker/ansible-service-broker-nightly"
  def packagesToBuild = [
    [
      package: "ansible-service-broker",
      org: "openshift",
      project: "ansible-service-broker"
    ],
    [
      package: "apb-base-scripts",
      org: "ansibleplaybookbundle",
      project: "apb-base"
    ],
    [
      package: "ansible-asb-modules",
      org: "ansibleplaybookbundle",
      project: "ansible-asb-modules"
    ],
    [
      package: "ansible-kubernetes-modules",
      org: "ansibleplaybookbundle",
      project: "ansible-kubernetes-modules"
    ],
    [
      package: "python-openshift",
      org: "ansibleplaybookbundle",
      project: "openshift-restclient-python"
    ],
    [
      package: "postgresql-apb-role",
      org: "ansibleplaybookbundle",
      project: "postgresql-apb"
    ],
    [
      package: "mariadb-apb-role",
      org: "ansibleplaybookbundle",
      project: "mariadb-apb"
    ],
    [
      package: "mediawiki-apb-role",
      org: "ansibleplaybookbundle",
      project: "mediawiki-apb"
    ],
    [
      package: "mysql-apb-role",
      org: "ansibleplaybookbundle",
      project: "mysql-apb"
    ],
  ]

  stages {
    stage('Build Packages') {
      def parallelStages = packagesToBuild.collectEntries {
        [
          "Building ${it.package} nightly": {
            node {
              echo "${it.package} ${it.org} ${it.project}"
            }
          }
        ]
      }
      parallel parallelStages
    }
    stage('Test') {
      steps {
        sh './scripts/test.sh'
      }
    }
    stage('Deploy') {
      steps {
        sh './scripts/deploy.sh'
      }
    }
  }
}

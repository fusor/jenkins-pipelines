#!groovy
def copr = "@ansible-service-broker/ansible-service-broker-nightly"
def projects = [
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

pipeline {
  agent any
  parameters {
    string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
  }
  stages {
    stage('Build nightly packages') {
      steps {
        script {
          def builds = projects.collectEntries {
            [
              "Build ${it.package} nightly" : {
                sh "./scripts/build.sh ${copr} ${it.package} ${it.org} ${it.project}"
              }
            ]
          }
          parallel builds
        }
      }
    }

    stage('Test') {
      steps {
        sh 'ls -lhat'
        sh 'ls -lhat ../'
        sh './scripts/test.sh'
      }
    }

    stage('Deploy') {
      steps {
        sh 'ls -lhat'
        sh 'ls -lhat ../'
        sh './scripts/deploy.sh'
      }
    }
  }
}

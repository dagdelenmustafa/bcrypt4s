# bcrypt4s
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.dagdelenmustafa/bcrypt4s/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.dagdelenmustafa/bcrypt4s)
![Build And Test](https://github.com/dagdelenmustafa/bcrypt4s/actions/workflows/build-test.yml/badge.svg)
![codecov](https://codecov.io/github/dagdelenmustafa/bcrypt4s/branch/main/graph/badge.svg?token=AQ61AS4SJ5)


## Setup

#### SBT
```scala
libraryDependencies += "io.github.dagdelenmustafa" % "bcrypt4s" % "{version}"
```

#### Maven
```xml
<dependency>
    <groupId>io.github.dagdelenmustafa</groupId>
    <artifactId>bcrypt4s</artifactId>
    <version>{version}</version>
</dependency>
```

## Usage
```scala
import com.mdagdelen.bcrypt4s

def run: F[Unit] = for {
  salt <- bcrypt4s.genSalt[F](logRounds = 12)
  hash <- bcrypt4s.hashPw[F]("1234", salt)
  isOk <- bcrypt4s.checkPw[F]("1234", hash)
  _ <- Console[F].println(isOk)
} yield ()
```

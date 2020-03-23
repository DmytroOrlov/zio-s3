/*
 * Copyright 2017-2020 John A. De Goes and the ZIO Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zio.s3

import java.time.Instant

import software.amazon.awssdk.services.s3.model._

import scala.jdk.CollectionConverters._

case class S3BucketListing(buckets: List[S3Bucket])

object S3BucketListing {

  def apply(resp: ListBucketsResponse): S3BucketListing =
    S3BucketListing(resp.buckets().asScala.toList.map(S3Bucket(_)))
}

case class S3Bucket(name: String, creationDate: Instant)

object S3Bucket {
  def apply(bucket: Bucket): S3Bucket = new S3Bucket(bucket.name(), bucket.creationDate())
}

case class S3ObjectListing(
    bucketName: String,
    objectSummaries: List[S3ObjectSummary],
    nextContinuationToken: Option[String]
)

object S3ObjectListing {

  def apply(r: ListObjectsV2Response): S3ObjectListing =
    S3ObjectListing(
      r.name(),
      r.contents().asScala.toList.map(o => S3ObjectSummary(r.name(), o.key())),
      Option(r.nextContinuationToken())
    )
}

case class S3ObjectSummary(bucketName: String, key: String)

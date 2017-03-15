(ns aws-dojo.s3
  (:require [amazonica.aws.s3 :as s3]
            [clojure.spec :as s])
  (:import (java.io ByteArrayInputStream)
           (java.time Instant)))

(def ^:dynamic *bucket-prefix* "clojure.aws-dojo.")
(def ^:dynamic *url-expiry-hours* 1)

(s/def :stream/input #(instance? ByteArrayInputStream %))
(s/def :stream/size nat-int?)
(s/def :stream/stream (s/keys :req [:stream/input
                                    :stream/size]))

(s/fdef ->stream
        :args (s/cat :s string?)
        :ret :stream/stream)

(defn- ->stream [s]
  (let [bs (.getBytes s)]
    {:stream/input (ByteArrayInputStream. bs)
     :stream/size (count bs)}))

(s/fdef expiry-ts
        :args (s/cat)
        :ret nat-int?)

(defn- expiry-ts []
  (-> (Instant/now)
      (.plusSeconds (* 3600 *url-expiry-hours*))
      (.toEpochMilli)))

(s/def :s3/key string?)
(s/def :s3/name string?)
(s/def :s3/url string?)
(s/def :s3/bucket (s/keys :req [:s3/name]))

(s/fdef create-bucket!
        :args (s/cat :name :s3/name)
        :ret :s3/bucket)

(defn create-bucket! [name])

(s/fdef put!
        :args (s/cat :bucket :s3/bucket
                     :key :s3/key
                     :val string?)
        :ret :s3/url)

(defn put! [bucket key val])

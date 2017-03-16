(ns aws-dojo.s3
  (:require [amazonica.aws.s3 :as s3])
  (:import (java.io ByteArrayInputStream)
           (java.time Instant)))

(def ^:dynamic *bucket-prefix* "clojure.aws-dojo.")
(def ^:dynamic *url-expiry-hours* 1)

(defn- ->stream [s]
  (let [bs (.getBytes s)]
    {:stream/input (ByteArrayInputStream. bs)
     :stream/size (count bs)}))

(defn- expiry-ts []
  (-> (Instant/now)
      (.plusSeconds (* 3600 *url-expiry-hours*))
      (.toEpochMilli)))

(defn create-bucket! [name])

(defn put! [bucket key val])

(ns scrambler.scrambler)

(defn validate-just-chars [param]
  (let [tst (re-matches #"^[a-z]+$" param)]
    (if (nil? tst)
      tst
      param)))

(defn validate-string [param]
  (if (string? param)
    param
    nil))

(defn validate [param]
  (let [tst (some-> param
                    (validate-string)
                    (validate-just-chars))]
    (not (nil? tst))))

(defn scramble? [superset subset]
  (if (and (validate superset) (validate subset))
    (let [sbs (seq subset)
          sps (seq superset)]
      (every? (fn [ch] (some #(= ch %) sps)) sbs))
    false))

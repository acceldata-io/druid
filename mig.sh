#!/bin/bash

WORDS=("flink" "nifi" "knox" "zeppelin" "registry" "druid" "spark3-warehouse-connector" "spark2-warehouse-connector" "phoenix" "ozone" "kafka3" "kafka" "impala" "ranger" "tez" "cruise-control" "hbase" "cruise-control3" "spark3" "hue" "hive" "oozie" "hadoop" "spark" "livy" "sqoop" "zookeeper" "kudu") 

ROOT_DIR="${1:-.}"

OUTPUT_FILE="matching_words_results.txt"

:> "$OUTPUT_FILE"
find "$ROOT_DIR" -type f -name "pom.xml" | while read -r POM_FILE; do
    for WORD in "${WORDS[@]}"; do
        grep -Hn "$WORD" "$POM_FILE" | while read -r LINE; do
            echo "Found '$WORD' in $POM_FILE: as $LINE" >> "$OUTPUT_FILE"
        done
    done
done

echo "Results saved in $OUTPUT_FILE"

#!/bin/sh

timelimit=10
requests=4000
concurrency=20

cd `dirname $0`

ab -g gnuplot/rails1_c20.tsv \
  -k \
  -t ${timelimit} -c ${concurrency} -n ${requests} \
  -T "application/x-www-form-urlencoded" -p post.txt http://127.0.0.1:3000/logs

ab -g gnuplot/rails2_c20.tsv \
  -k \
  -t ${timelimit} -c ${concurrency} -n ${requests} \
  -T "application/x-www-form-urlencoded" -p post.txt http://127.0.0.1:3000/logs/async

ab -g gnuplot/jax-rs1_c20.tsv \
  -k \
  -t ${timelimit} -c ${concurrency} -n ${requests} \
  -T "application/x-www-form-urlencoded" -p post.txt http://127.0.0.1:8080/logs

ab -g gnuplot/jax-rs2_c20.tsv \
  -k \
  -t ${timelimit} -c ${concurrency} -n ${requests} \
  -T "application/x-www-form-urlencoded" -p post.txt http://127.0.0.1:8080/logs/async

ab -g gnuplot/play1_c20.tsv \
  -k \
  -t ${timelimit} -c ${concurrency} -n ${requests} \
  -T "application/x-www-form-urlencoded" -p post.txt http://127.0.0.1:9000/logs

ab -g gnuplot/play2_c20.tsv \
  -k \
  -t ${timelimit} -c ${concurrency} -n ${requests} \
  -T "application/x-www-form-urlencoded" -p post.txt http://127.0.0.1:9000/logs/async

ab -g gnuplot/node1_c20.tsv \
  -k \
  -t ${timelimit} -c ${concurrency} -n ${requests} \
  -T "application/x-www-form-urlencoded" -p post.txt http://127.0.0.1:8124/logs

ab -g gnuplot/node2_c20.tsv \
  -k \
  -t ${timelimit} -c ${concurrency} -n ${requests} \
  -T "application/x-www-form-urlencoded" -p post.txt http://127.0.0.1:8124/logs/async

gnuplot gnuplot/template_c20.p

echo
echo "Check gnuplot/c20.png!"
echo


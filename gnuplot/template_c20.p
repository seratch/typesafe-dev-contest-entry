set terminal png
set output "gnuplot/c20.png"
set title "curl -XPOST /logs/(async) -d'name=access_log&line=xxx'"
set size 1,1
set grid y
set xlabel "requests"
set ylabel "response time (ms)"
plot "gnuplot/rails1_c20.tsv" using 9 smooth sbezier with lines title "Rails 3.2", \
     "gnuplot/jax-rs1_c20.tsv" using 9 smooth sbezier with lines title "Jersey 1.13", \
     "gnuplot/node1_c20.tsv" using 9 smooth sbezier with lines title "Express 3.0", \
     "gnuplot/play1_c20.tsv" using 9 smooth sbezier with lines title "Play 2.0", \
     "gnuplot/rails2_c20.tsv" using 9 smooth sbezier with lines title "Rails 3.2(async)", \
     "gnuplot/jax-rs2_c20.tsv" using 9 smooth sbezier with lines title "Jersey 1.13(async)", \
     "gnuplot/node2_c20.tsv" using 9 smooth sbezier with lines title "Express 3.0(async)", \
     "gnuplot/play2_c20.tsv" using 9 smooth sbezier with lines title "Play 2.0(async)"


C:\\ffmpeg\\bin\\ffmpeg image2pipe -i gource.ppm -vcodec libx264 -vpre max -threads 0 -mbd rd -flags mv0 -trellis 2 -cmp 2 -subcmp 2 -g 300 -pass 1/2 -b <3000000> gource.mp4
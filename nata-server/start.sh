# ./minicap/run.sh -P 1080x1920@270x480/0 &
adb forward tcp:1313 localabstract:minicap
# sudo mongod &
pm2 start ./bin/www
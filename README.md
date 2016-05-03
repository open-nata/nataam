# NataAM(Advanced Monkey for NATA platform)
NataAM is built for automating the process of UI Testing .


# 图标
![](images/logo.jpg)

# 思维导图
![](images/mind.png)

# 测试流程
![](images/procedure.png)

# 运行步骤
- ./minicap/run.sh -P 1080x1920@270x480/0 
- adb forward tcp:1313 localabstract:minicap
- sudo mongod 
- pm2 start ./bin/www

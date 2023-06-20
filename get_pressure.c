#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include <wiringPi.h>
#include <wiringPiI2C.h>

#include <fcntl.h>
#include <sys/ioctl.h>
#include <linux/i2c-dev.h>

int main() {
    int dev_fd;
    int data;
    dev_fd = wiringPiI2CSetup(0x8);
    if (dev_fd < 0) {
        printf("Error\n");
        return 0;
    }

    data = wiringPiI2CReadReg16(dev_fd, 0x0);

    printf("pressure = %d\n", data);

    return 0;
}
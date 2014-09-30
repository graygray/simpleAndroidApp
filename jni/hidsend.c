/*
 * Hidraw Userspace Example
 *
 * Copyright (c) 2010 Alan Ott <alan@signal11.us>
 * Copyright (c) 2010 Signal 11 Software
 *
 * The code may be used by anyone for any purpose,
 * and can serve as a starting point for developing
 * applications using hidraw.
 */

/* Linux */
#include <linux/types.h>
#include <input.h>
//#include <hidraw.h>

/*
 * Ugly hack to work around failing compilation on systems that don't
 * yet populate new version of hidraw.h to userspace.
 *
 * If you need this, please have your distro update the kernel headers.
 */
#ifndef HIDIOCSFEATURE
#define HIDIOCSFEATURE(len)    _IOC(_IOC_WRITE|_IOC_READ, 'H', 0x06, len)
#define HIDIOCGFEATURE(len)    _IOC(_IOC_WRITE|_IOC_READ, 'H', 0x07, len)
#endif

/* Unix */
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

/* C */
//#include <stdio.h>
//#include <string.h>
//#include <stdlib.h>
//#include <errno.h>

/* Returned Error Code */
#define ERR_ARGS 			1
#define ERR_OPEN 			2
#define ERR_HIDIOCGRAWNAME 	3
#define ERR_HIDIOCGRAWPHYS 	4
#define ERR_HIDIOCGRAWINFO 	5
#define ERR_WRITE 			6

const char *bus_str(int bus);
int asciiToHex(unsigned char *hex_str);

int main(int argc, char **argv)
{
//	int fd;
//	int i, res, desc_size = 0;
//	char buf[256];
//	struct hidraw_devinfo info;
//	int data_length = 0;
//
//	if (argc < 3) {
//		printf("==============================================\n");
//		printf("The following data field must be in hex !!!\n");
//		printf("Usage: %s report_id data_1 data_2 data_3 ... \n", argv[0]);
//		printf("Example: %s 01 11 22 aa bb \n", argv[0]);
//		printf("==============================================\n");
//		return -ERR_ARGS;
//	}
//	/* Open the Device with non-blocking reads. In real life,
//	   don't use a hard coded path; use libudev instead. */
//	fd = open("/dev/hidraw0", O_RDWR | O_NONBLOCK);
//
//	if (fd < 0) {
//		printf("errno = %d\n", errno);
//		perror("Unable to open device");
//		return -ERR_OPEN;
//	}
//
//	memset(&info, 0x0, sizeof(info));
//	memset(buf, 0x0, sizeof(buf));
//
//	/* Get Raw Name */
//	res = ioctl(fd, HIDIOCGRAWNAME(256), buf);
//	if (res < 0) {
//		perror("HIDIOCGRAWNAME");
//		close(fd);
//		return -ERR_HIDIOCGRAWNAME;
//	} else {
//		printf("Raw Name: %s\n", buf);
//	}
//	/* Get Physical Location */
//	res = ioctl(fd, HIDIOCGRAWPHYS(256), buf);
//	if (res < 0) {
//		perror("HIDIOCGRAWPHYS");
//		close(fd);
//		return -ERR_HIDIOCGRAWPHYS;
//	} else {
//		printf("Raw Phys: %s\n", buf);
//	}
//	/* Get Raw Info */
//	res = ioctl(fd, HIDIOCGRAWINFO, &info);
//	if (res < 0) {
//		perror("HIDIOCGRAWINFO");
//		close(fd);
//		return -ERR_HIDIOCGRAWINFO;
//	} else {
//		printf("Raw Info:\n");
//		printf("\tbustype: %d (%s)\n",
//			info.bustype, bus_str(info.bustype));
//		printf("\tvendor: 0x%04hx\n", info.vendor);
//		printf("\tproduct: 0x%04hx\n", info.product);
//	}
//
//	/* Prepare report data */
//	data_length = argc - 1;
//	for (i = 0; i < data_length; i++) {
//		buf[i] = asciiToHex(argv[i + 1]);
//	}
//
//	printf("write length = %d, data = ", data_length);
//	for (i = 0; i < data_length ; i++) {
//		printf("0x%x ", buf[i]);
//	}
//	printf("\n");
//
//	/* Send a Report to the Device */
//	res = write(fd, buf, data_length);
//	if (res < 0) {
//		printf("Error: %d\n", errno);
//		perror("write");
//		close(fd);
//		return -ERR_WRITE;
//	} else {
//		printf("write() wrote %d bytes successfully\n", res);
//	}
//
//	close(fd);
	return 0;
}

/* Convert ascii string to hex value */
int asciiToHex(unsigned char *hex_str)
{
	int n = strlen(hex_str); // where hex_str is pointer to hex string
	int sum = 0;
	int leftshift = 0;
	
	while (n > 0)
	{
		if ((hex_str[n-1] >= '0') && (hex_str[n-1] <= '9'))
			sum += (hex_str[n-1] - '0') << leftshift;
		if ((hex_str[n-1] >= 'A') && (hex_str[n-1] <= 'F'))
			sum += (hex_str[n-1] - 'A' + 10) << leftshift;
		if ((hex_str[n-1]>='a') && (hex_str[n-1]<='f'))
			sum += (hex_str[n-1] - 'a' + 10) << leftshift;
		n--;
		leftshift += 4;
	}
	return sum;
}

/* Identify bus type */
const char *bus_str(int bus)
{
	switch (bus) {
	case BUS_USB:
		return "USB";
		break;
	case BUS_HIL:
		return "HIL";
		break;
	case BUS_BLUETOOTH:
		return "Bluetooth";
		break;
	case BUS_VIRTUAL:
		return "Virtual";
		break;
	default:
		return "Other";
		break;
	}
}

int test(){

	int fd, res, index;
	char buf[512];

	char dev_path[256] = "/dev/input/event4";
	fd = open(dev_path, O_RDONLY);
//	fd = open("/dev/input/event3", O_RDWR | O_NONBLOCK);
//	fd = open("/dev/hidraw0", O_RDONLY); // NOK
//	fd = open("/dev/hidraw0", O_RDWR | O_NONBLOCK); // NOK

	if (fd < 0) {
//		printf("errno = %d\n", errno);
//		perror("Unable to open device");
	}

	memset(buf, 0x0, sizeof(buf));

	res = read(fd, buf, sizeof(buf));
	if (res < 0 ) {
//		printf("Error: %d, res:%d\n", errno, res);
//		perror("read");
		close(fd);
		return -ERR_WRITE;
	} else {
		printf("\n=============================read %d bytes at %s \n", res, dev_path);
		for (index = 0; index < res; ++index) {
			printf("0x%02x ", buf[index]);
		}
	}

	close(fd);
	return res;
}

char* readDevice(const char* path){

	int fd, res, index;
	char buf[512];
	char temp_string[512];
	char output_string[512];

//	char dev_path[256] = "/dev/input/event4";
	// cant use hidrawx
//	char dev_path[256] = "/dev/hidraw0";
	fd = open(path, O_RDONLY);
//	fd = open(dev_path, O_RDONLY);
//	fd = open("/dev/input/event3", O_RDWR | O_NONBLOCK);
//	fd = open("/dev/hidraw0", O_RDONLY); // NOK
//	fd = open("/dev/hidraw0", O_RDWR | O_NONBLOCK); // NOK

//	sprintf("%s\n", path);
	sprintf(output_string, "%s\n", path);

	if (fd < 0) {
//		printf("errno = %d\n", errno);
//		perror("Unable to open device");
	}

	memset(buf, 0x0, sizeof(buf));
	memset(temp_string, 0x0, sizeof(buf));
	memset(output_string, 0x0, sizeof(buf));

	res = read(fd, buf, sizeof(buf));
	if (res < 0 ) {
//		printf("Error: %d, res:%d\n", errno, res);
//		perror("read");
		close(fd);
		return -ERR_WRITE;
	} else {
//		printf("\n=============================read %d bytes at %s \n", res, dev_path);
//		sprintf(output_string, "open:%d, read:%d\n", fd, res);
		for (index = 0; index < res; ++index) {
//			printf("0x%02x ", buf[index]);
			sprintf(temp_string, "0x%02x ", buf[index]);
			strcat(output_string, temp_string);
		}
	}

	close(fd);
	return output_string;
}


/* End of file */

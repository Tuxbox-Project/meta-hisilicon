#!/bin/sh

echo "starting firstboot script"
echo "Firstboot" > /dev/dbox/oled0

if [ -b "/dev/disk/by-partlabel/linuxrootfs" ]; then
	echo "Resizing linuxrootfs partition"
	resize2fs /dev/disk/by-partlabel/linuxrootfs
fi

if [ -b /dev/disk/by-partlabel/userdata ]; then
	echo "Resizing userdata partition"
	resize2fs /dev/disk/by-partlabel/userdata
fi

if [ ! -f /mnt/userdata/swapfile ]; then
	[ ! -d /mnt/userdata ] && mkdir /mnt/userdata
	mountpoint -q /mnt/userdata || mount -l /dev/disk/by-partlabel/userdata /mnt/userdata
	echo "Creating swapfile"
	dd if=/dev/zero of=/mnt/userdata/swapfile bs=1024 count=204800
	chmod 600 /mnt/userdata/swapfile
	mkswap -L swapspace /mnt/userdata/swapfile
	swapon /mnt/userdata/swapfile
fi

grep -q /mnt/userdata/swapfile /proc/swaps || swapon /mnt/userdata/swapfile
grep -q /mnt/userdata/swapfile /etc/fstab || echo "/mnt/userdata/swapfile none swap defaults 0 0" >> /etc/fstab

echo "first boot script work done"
#job done, remove it from systemd services
systemctl disable firstboot.service
echo "firstboot script disabled"
echo > /dev/dbox/oled0

[ -f /usr/bin/etckeeper ] && /etc/etckeeper/update_etc.sh


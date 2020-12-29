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

if [ ! -f /swapfile ]; then
	echo "Creating swapfile"
	dd if=/dev/zero of=/swapfile bs=1024 count=204800
	chmod 600 /swapfile
	mkswap -L swapspace /swapfile
fi

grep -q /swapfile /proc/swaps || swapon /mnt/userdata/swapfile
grep -q /swapfile /etc/fstab || echo "/swapfile none swap defaults 0 0" >> /etc/fstab


echo "change hostname in /etc/hosts & /etc/hostname
"
OLDHOST=`hostname`
NEWHOST=$OLDHOST-`echo $(($RANDOM % 100+1000))`
sed -i "s/$OLDHOST/$NEWHOST/g" /etc/hosts
sed -i "s/$OLDHOST/$NEWHOST/g" /etc/hostname

echo "first boot script work done"
#job done, remove it from systemd services
systemctl disable firstboot.service
echo "firstboot script disabled"
echo > /dev/dbox/oled0

[ -f /usr/bin/etckeeper ] && /etc/etckeeper/update_etc.sh


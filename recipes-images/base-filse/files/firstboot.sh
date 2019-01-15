#!/bin/sh

echo "starting firstboot script"
echo "Firstboot" > /dev/dbox/oled0

[ -f /usr/bin/etckeeper ] && /etc/etckeeper/update_etc.sh

echo "first boot script work done"
#job done, remove it from systemd services
systemctl disable firstboot.service
echo "firstboot script disabled"

KV = "4.4.35"
SRCDATE = "20200622"

PROVIDES = "virtual/blindscan-dvbs"
PROVIDES  = "virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/dvb-driver"
RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
	
require hd-dvb-modules.inc

SRC_URI[md5sum] = "71727df0e1354442c069d86c4cbb2c83"
SRC_URI[sha256sum] = "5c8278a7676f49a4777cf25117f0c66ca349ae6661dbf5daf62c9ff0a319b385"

do_configure[noexec] = "1"

do_compile_append () {
	cat > suspend << EOF
#!/bin/sh

if [ \$1 = "poweroff" ]; then
        mount -t sysfs sys /sys
        /usr/bin/turnoff_power
fi

EOF
}

do_install_append() {
	install -d ${D}${bindir} -d ${D}${systemd_unitdir}/system-shutdown
	install -m 0755 ${S}/suspend ${D}${systemd_unitdir}/system-shutdown
	install -m 0755 ${S}/turnoff_power ${D}${bindir}
}

do_package_qa() {
}

FILES_${PN} += "${bindir} ${sysconfdir}/init.d ${systemd_unitdir}"

INSANE_SKIP_${PN} += "already-stripped"

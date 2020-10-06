SUMMARY = "Hardware drivers for ${MACHINE_DRIVER}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

KV = "4.4.35"
PV = "${KV}+${SRCDATE}"

PROVIDES = "virtual/blindscan-dvbs"
PROVIDES  = "virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/dvb-driver"
RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"

SRC_URI = "http://source.mynonpublic.com/gfutures/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

S = "${WORKDIR}"

do_configure() {
	cat > suspend << EOF
#!/bin/sh

if [ \$1 = "poweroff" ]; then
        mount -t sysfs sys /sys
        /usr/bin/turnoff_power
fi

EOF
}

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -d ${D}/${sysconfdir}/modules-load.d
	install -m 0755 ${S}/*.ko ${D}${base_libdir}/modules/${KV}/extra
	for i in ${MACHINE}_1 ${MACHINE}_2 ${MACHINE}_3 ${MACHINE}_4; do
		echo $i >> ${D}/${sysconfdir}/modules-load.d/${MACHINE}.conf
	done
}

do_install_append() {
	install -d ${D}${bindir} -d ${D}${systemd_unitdir}/system-shutdown
	install -m 0755 ${S}/suspend ${D}${systemd_unitdir}/system-shutdown
	install -m 0755 ${S}/turnoff_power ${D}${bindir}
}

do_package_qa() {
}

pkg_prerm_${PN}() {
	if [ "x$D" == "x" ]; then
		if [ -f /lib/modules/${KV}/extra/hi_play.ko ] ; then
			rm -f /lib/modules/${KV}/extra/hi_play.ko;
		fi
	fi
}

FILES_${PN} += "${sysconfdir} \
		${bindir} \
		${base_libdir} \
"

INSANE_SKIP_${PN} += "already-stripped"


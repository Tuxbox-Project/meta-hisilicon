DEPENDS_append += " \
        hd-partitions-${MACHINE} \
        hd-bootargs-${MACHINE} \
	hd-recovery-${MACHINE} \
"

IMAGE_INSTALL += " \
	hd-dvb-modules-${MACHINE} \
	hd-libs-${HICHIPSET} \
	hd-mali-${HICHIPSET} \
	kernel-module-mali-${HICHIPSET} \
"

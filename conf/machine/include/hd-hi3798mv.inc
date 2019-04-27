# We know about rtc
MACHINE_FEATURES_BACKFILL_CONSIDERED = "rtc"

PREFERRED_VERSION_linux-hd = "4.4.35"

#Provider
PREFERRED_PROVIDER_virtual/kernel ?= "linux-hd"
PREFERRED_PROVIDER_virtual/egl ?= "hd-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles1 ?= "hd-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "hd-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libopencl ?= "hd-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs ?= "hd-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbc ?= "hd-dvb-modules-${MACHINE}"

#require conf/machine/include/arm/arch-armv8.inc
require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/hd-essential.inc

# Kodi
EXTRA_OECONF_append_pn-kodi += " --with-gpu=mali"

MACHINE_EXTRA_RRECOMMENDS = " \
	ffmpeg \
	ntfs-3g \
"

MACHINE_FEATURES += " alsa usbhost wlan"

IMAGEVERSION := "${DISTRO_NAME}-${DISTRO_VERSION}-${DATE}"
IMAGEVERSION[vardepsexclude] = "DATE"
IMAGEDIR ?= "hd"
IMAGEDIRRECOVERY ?= "${MACHINE}-recovery"

image_packaging() {
	mkdir -p ${IMGDEPLOYDIR}/${IMAGEDIR}/${MACHINE}; \
	mkdir -p ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/${MACHINE}; \
	cp ${DEPLOY_DIR_IMAGE}/uImage ${IMGDEPLOYDIR}/${IMAGEDIR}/${MACHINE}/uImage; \
	cp ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.tar.bz2 ${IMGDEPLOYDIR}/${IMAGEDIR}/${MACHINE}/rootfs.tar.bz2; \
	echo "${IMAGEVERSION}" > ${IMGDEPLOYDIR}/${IMAGEDIR}/${MACHINE}/imageversion; \
	echo "${IMAGEVERSION}_emmc.zip" > ${IMGDEPLOYDIR}/${IMAGEDIR}/unforce_${MACHINE}.txt; \
	echo "Rename the unforce_${MACHINE}.txt to force_${MACHINE}.txt and move it to the root of your usb-stick" > ${IMGDEPLOYDIR}/${IMAGEDIR}/force_${MACHINE}_READ.ME; \
	echo "When you enter the recovery menu then it will force to install the image ${IMAGEVERSION}_emmc.zip in the image-slot1" >> ${IMGDEPLOYDIR}/${IMAGEDIR}/force_${MACHINE}_READ.ME; \
	cp ${IMGDEPLOYDIR}/bootoptions.img ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/${MACHINE}/bootoptions.img; \
	cp ${DEPLOY_DIR_IMAGE}/hd-partitions-${MACHINE}/fastboot.bin ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/fastboot.bin; \
	cp ${DEPLOY_DIR_IMAGE}/hd-partitions-${MACHINE}/bootargs-8gb.bin ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/bootargs.bin; \
	cp ${DEPLOY_DIR_IMAGE}/hd-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/${MACHINE}/fastboot.bin; \
	cp ${DEPLOY_DIR_IMAGE}/hd-partitions-${MACHINE}/${MACHINE}/bootargs-8gb.bin ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/${MACHINE}/bootargs.bin; \
	cp ${DEPLOY_DIR_IMAGE}/hd-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/${MACHINE}/pq_param.bin; \
	cp ${DEPLOY_DIR_IMAGE}/hd-partitions-${MACHINE}/${MACHINE}/baseparam.img ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/${MACHINE}/baseparam.img; \
	cp ${DEPLOY_DIR_IMAGE}/hd-partitions-${MACHINE}/${MACHINE}/logo.img ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/${MACHINE}/logo.img; \
	cp ${DEPLOY_DIR_IMAGE}/hd-partitions-${MACHINE}/${MACHINE}/recovery.bin ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}/${MACHINE}/recovery.bin; \
	echo "To access the recovery image press immediately by power-up the frontpanel button or hold down a remote button key untill the display says boot" > ${IMGDEPLOYDIR}/${IMAGEDIR}/${MACHINE}/recovery.txt; \
	cd ${IMGDEPLOYDIR}/${IMAGEDIRRECOVERY}; \
	zip -r ../${DISTRO_NAME}-${DISTRO_VERSION}-${MACHINE}_recovery_emmc.zip *; \
	cd ..; \
	rm -Rf ${IMAGEDIRRECOVERY}; \
	cd ${IMGDEPLOYDIR}/${IMAGEDIR}; \
	zip -r ../${DISTRO_NAME}-${DISTRO_VERSION}-${MACHINE}_mmc.zip unforce_${MACHINE}.txt force_${MACHINE}_READ.ME ${MACHINE}/imageversion ${MACHINE}/uImage ${MACHINE}/rootfs.tar.bz2; \
	cd ..; \
	rm -Rf ${IMAGEDIR}; \
}

IMAGE_POSTPROCESS_COMMAND += "image_packaging; "

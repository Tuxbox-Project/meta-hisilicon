ANY_OF_DISTRO_FEATURES_class-target = ""

DEPENDS_append += "libdrm elfutils"

PACKAGECONFIG_class-target ??= "${@bb.utils.filter('DISTRO_FEATURES', 'wayland vulkan', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11 opengl', 'x11 dri3', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11 vulkan', 'dri3', '', d)} \
                   glx-tls \
                   gallium \
                   opengl \
                   egl \
                   gles \
                   lima \
                   gbm \
                   dri \
		   "

do_install_append() {
    # Remove Mesa libraries (EGL, GLESv1, GLESv2, GBM)
    # provided by SOC
    rm -f ${D}${libdir}/libGLESv1_CM.*
    rm -f ${D}${libdir}/libEGL.so.*
    rm -f ${D}${libdir}/libEGL.so
    rm -f ${D}${libdir}/libgbm.so.*
    rm -f ${D}${libdir}/libgbm.so
    rm -f ${D}${libdir}/libGLESv1*.so.*
    rm -f ${D}${libdir}/libGLESv2.so.*
    rm -f ${D}${libdir}/libGLESv2.so
}
	
PROVIDES_remove = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/libgbm"

cmd_drivers/mstar/pm_addr/mdrv_pm_proc.o := mips-linux-gnu-gcc -Wp,-MD,drivers/mstar/pm_addr/.mdrv_pm_proc.o.d  -nostdinc -isystem /home/Supernova/mips-4.3/bin/../lib/gcc/mips-linux-gnu/4.3.2/include -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include -Iarch/mips/include/generated -Iinclude  -include /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/include/linux/kconfig.h -D__KERNEL__ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/amethyst/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/emerald/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/kaiserin/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/kappa/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/kriti/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/kratos/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/kenya/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/keres/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/keltic/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/nugget/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/nikon/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/milan/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/mulan/include/ -D"VMLINUX_LOAD_ADDRESS=0xffffffff80000000" -D"DATAOFFSET=0" -Wall -Wundef -Wstrict-prototypes -Wno-trigraphs -fno-strict-aliasing -fno-common -Werror-implicit-function-declaration -Wno-format-security -fno-delete-null-pointer-checks -O2 -mno-check-zero-division -mabi=32 -G 0 -mno-abicalls -fno-pic -pipe -msoft-float -ffreestanding -EL -UMIPSEB -U_MIPSEB -U__MIPSEB -U__MIPSEB__ -UMIPSEL -U_MIPSEL -U__MIPSEL -U__MIPSEL__ -DMIPSEL -D_MIPSEL -D__MIPSEL -D__MIPSEL__ -march=mips32r2 -Wa,-mips32r2 -Wa,--trap -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/generic/include/ -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-malta -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/mips-boards/milan -I/home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-generic -fno-stack-protector -fomit-frame-pointer -Wdeclaration-after-statement -Wno-pointer-sign -fno-strict-overflow -Idrivers/mstar/include -DTITANIA -DMSOS_TYPE_LINUX    -D"KBUILD_STR(s)=\#s" -D"KBUILD_BASENAME=KBUILD_STR(mdrv_pm_proc)"  -D"KBUILD_MODNAME=KBUILD_STR(mdrv_pm_addr)" -c -o drivers/mstar/pm_addr/mdrv_pm_proc.o drivers/mstar/pm_addr/mdrv_pm_proc.c

source_drivers/mstar/pm_addr/mdrv_pm_proc.o := drivers/mstar/pm_addr/mdrv_pm_proc.c

deps_drivers/mstar/pm_addr/mdrv_pm_proc.o := \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/include/linux/kconfig.h \
    $(wildcard include/config/h.h) \
    $(wildcard include/config/.h) \
    $(wildcard include/config/foo.h) \
  include/linux/fs.h \
    $(wildcard include/config/sysfs.h) \
    $(wildcard include/config/smp.h) \
    $(wildcard include/config/fs/posix/acl.h) \
    $(wildcard include/config/security.h) \
    $(wildcard include/config/quota.h) \
    $(wildcard include/config/fsnotify.h) \
    $(wildcard include/config/ima.h) \
    $(wildcard include/config/preempt.h) \
    $(wildcard include/config/epoll.h) \
    $(wildcard include/config/debug/writecount.h) \
    $(wildcard include/config/file/locking.h) \
    $(wildcard include/config/auditsyscall.h) \
    $(wildcard include/config/block.h) \
    $(wildcard include/config/debug/lock/alloc.h) \
    $(wildcard include/config/fs/xip.h) \
    $(wildcard include/config/migration.h) \
  include/linux/limits.h \
  include/linux/ioctl.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/ioctl.h \
  include/asm-generic/ioctl.h \
  include/linux/blk_types.h \
    $(wildcard include/config/blk/dev/integrity.h) \
  include/linux/types.h \
    $(wildcard include/config/mp/debug/tool/changelist.h) \
    $(wildcard include/config/uid16.h) \
    $(wildcard include/config/lbdaf.h) \
    $(wildcard include/config/arch/dma/addr/t/64bit.h) \
    $(wildcard include/config/phys/addr/t/64bit.h) \
    $(wildcard include/config/64bit.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/types.h \
    $(wildcard include/config/64bit/phys/addr.h) \
  include/asm-generic/int-ll64.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/bitsperlong.h \
  include/asm-generic/bitsperlong.h \
  include/linux/release_version.h \
  include/linux/posix_types.h \
  include/linux/stddef.h \
  include/linux/compiler.h \
    $(wildcard include/config/sparse/rcu/pointer.h) \
    $(wildcard include/config/trace/branch/profiling.h) \
    $(wildcard include/config/profile/all/branches.h) \
    $(wildcard include/config/enable/must/check.h) \
    $(wildcard include/config/enable/warn/deprecated.h) \
  include/linux/compiler-gcc.h \
    $(wildcard include/config/arch/supports/optimized/inlining.h) \
    $(wildcard include/config/optimize/inlining.h) \
  include/linux/compiler-gcc4.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/posix_types.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/sgidefs.h \
  include/mstar/mpatch_macro.h \
    $(wildcard include/config/mp/platform/arch/general.h) \
    $(wildcard include/config/mp/platform/arch/general/debug.h) \
    $(wildcard include/config/mp/platform/arm.h) \
    $(wildcard include/config/mp/platform/arm/debug.h) \
    $(wildcard include/config/mp/platform/mips.h) \
    $(wildcard include/config/mp/platform/mips/debug.h) \
    $(wildcard include/config/mp/platform/fixme.h) \
    $(wildcard include/config/mp/platform/fixme/debug.h) \
    $(wildcard include/config/mp/platform/arm/pmu.h) \
    $(wildcard include/config/mp/platform/arm/pmu/debug.h) \
    $(wildcard include/config/mp/platform/pm.h) \
    $(wildcard include/config/mp/platform/pm/debug.h) \
    $(wildcard include/config/mp/platform/arm/errata/775420.h) \
    $(wildcard include/config/mp/platform/arm/errata/775420/debug.h) \
    $(wildcard include/config/mp/platform/mstar/legancy/intr.h) \
    $(wildcard include/config/mp/platform/mstar/legancy/intr/debug.h) \
    $(wildcard include/config/mp/platform/sw/patch/l2/sram/rma.h) \
    $(wildcard include/config/mp/platform/sw/patch/l2/sram/rma/debug.h) \
    $(wildcard include/config/mp/platform/mips74k/timer.h) \
    $(wildcard include/config/mp/platform/mips74k/timer/debug.h) \
    $(wildcard include/config/mp/platform/arm/mstar/etm.h) \
    $(wildcard include/config/mp/platform/arm/mstar/etm/debug.h) \
    $(wildcard include/config/mp/platform/int/1/to/1/spi.h) \
    $(wildcard include/config/mp/platform/int/1/to/1/spi/debug.h) \
    $(wildcard include/config/mp/platform/disable/jiffies/overflow/debug.h) \
    $(wildcard include/config/mp/platform/disable/jiffies/overflow/debug/debug.h) \
    $(wildcard include/config/mp/platform/lzma/bin/compressed.h) \
    $(wildcard include/config/mp/platform/lzma/bin/compressed/debug.h) \
    $(wildcard include/config/mp/platform/cpu/setting.h) \
    $(wildcard include/config/mp/platform/cpu/setting/debug.h) \
    $(wildcard include/config/mp/platform/mips/system/call/get/version.h) \
    $(wildcard include/config/mp/platform/mips/system/call/get/version/debug.h) \
    $(wildcard include/config/mp/platform/verify/lx/mem/align.h) \
    $(wildcard include/config/mp/platform/verify/lx/mem/align/debug.h) \
    $(wildcard include/config/mp/platform/utopia2/interrupt.h) \
    $(wildcard include/config/mp/platform/utopia2/interrupt/debug.h) \
    $(wildcard include/config/mp/platform/utopia2k/export/symbol.h) \
    $(wildcard include/config/mp/platform/utopia2k/export/symbol/debug.h) \
    $(wildcard include/config/mp/platform/arm/ttbr1/instead/of/reserved/context/id.h) \
    $(wildcard include/config/mp/platform/arm/ttbr1/instead/of/reserved/context/id/debug.h) \
    $(wildcard include/config/mp/platform/binder/corlor/warning/message.h) \
    $(wildcard include/config/mp/platform/binder/corlor/warning/message/debug.h) \
    $(wildcard include/config/mp/platform/entropy/from/irq.h) \
    $(wildcard include/config/mp/platform/entropy/from/irq/debug.h) \
    $(wildcard include/config/mp/nand/ubi.h) \
    $(wildcard include/config/mp/nand/ubi/debug.h) \
    $(wildcard include/config/mp/nand/mtd.h) \
    $(wildcard include/config/mp/nand/mtd/debug.h) \
    $(wildcard include/config/mp/nand/ubifs.h) \
    $(wildcard include/config/mp/nand/ubifs/debug.h) \
    $(wildcard include/config/mp/nand/spansion.h) \
    $(wildcard include/config/mp/nand/spansion/debug.h) \
    $(wildcard include/config/mp/nand/bbt.h) \
    $(wildcard include/config/mp/nand/bbt/debug.h) \
    $(wildcard include/config/mp/nand/bbt/size.h) \
    $(wildcard include/config/mp/nand/bbt/size/debug.h) \
    $(wildcard include/config/mp/usb/mstar.h) \
    $(wildcard include/config/mp/usb/mstar/debug.h) \
    $(wildcard include/config/mp/sd/mstar.h) \
    $(wildcard include/config/mp/sd/mstar/debug.h) \
    $(wildcard include/config/mp/sd/plug.h) \
    $(wildcard include/config/mp/sd/plug/debug.h) \
    $(wildcard include/config/mp/emmc/partition.h) \
    $(wildcard include/config/mp/emmc/partition/debug.h) \
    $(wildcard include/config/mp/emmc/mmc/patch.h) \
    $(wildcard include/config/mp/emmc/mmc/patch/debug.h) \
    $(wildcard include/config/mp/emmc/trim.h) \
    $(wildcard include/config/mp/emmc/trim/debug.h) \
    $(wildcard include/config/mp/emmc/cache.h) \
    $(wildcard include/config/mp/emmc/cache/debug.h) \
    $(wildcard include/config/mp/emmc/datatag.h) \
    $(wildcard include/config/mp/emmc/datatag/debug.h) \
    $(wildcard include/config/mp/jbd2/commit/num/limit.h) \
    $(wildcard include/config/mp/jbd2/commit/num/limit/debug.h) \
    $(wildcard include/config/mp/jbd2/reset/journal/sb.h) \
    $(wildcard include/config/mp/jbd2/reset/journal/sb/debug.h) \
    $(wildcard include/config/mp/mstar/str/base.h) \
    $(wildcard include/config/mp/mstar/str/base/debug.h) \
    $(wildcard include/config/mp/android/low/mem/killer/include/cachemem.h) \
    $(wildcard include/config/mp/android/low/mem/killer/include/cachemem/debug.h) \
    $(wildcard include/config/mp/android/dummy/mstar/rtc.h) \
    $(wildcard include/config/mp/android/dummy/mstar/rtc/debug.h) \
    $(wildcard include/config/mp/android/alarm/clock/boottime/patch.h) \
    $(wildcard include/config/mp/android/alarm/clock/boottime/patch/debug.h) \
    $(wildcard include/config/mp/android/mstar/rc/map/define.h) \
    $(wildcard include/config/mp/android/mstar/rc/map/define/debug.h) \
    $(wildcard include/config/mp/android/mstar/hardcode/lpj.h) \
    $(wildcard include/config/mp/android/mstar/hardcode/lpj/debug.h) \
    $(wildcard include/config/mp/compiler/error.h) \
    $(wildcard include/config/mp/compiler/error/debug.h) \
    $(wildcard include/config/mp/debug/tool/watchdog.h) \
    $(wildcard include/config/mp/debug/tool/watchdog/debug.h) \
    $(wildcard include/config/mp/debug/tool/codedump.h) \
    $(wildcard include/config/mp/debug/tool/codedump/debug.h) \
    $(wildcard include/config/mp/debug/tool/codedump/data/sync.h) \
    $(wildcard include/config/mp/debug/tool/codedump/data/sync/debug.h) \
    $(wildcard include/config/mp/debug/tool/coredump/path.h) \
    $(wildcard include/config/mp/debug/tool/coredump/path/debug.h) \
    $(wildcard include/config/mp/debug/tool/coredump/path/option.h) \
    $(wildcard include/config/mp/debug/tool/coredump/path/option/debug.h) \
    $(wildcard include/config/mp/debug/tool/coredump/detect/usb/plugin.h) \
    $(wildcard include/config/mp/debug/tool/coredump/detect/usb/plugin/debug.h) \
    $(wildcard include/config/mp/debug/tool/coredump/build/in/usb.h) \
    $(wildcard include/config/mp/debug/tool/coredump/build/in/usb/debug.h) \
    $(wildcard include/config/mp/debug/tool/coredump/without/compress.h) \
    $(wildcard include/config/mp/debug/tool/coredump/without/compress/debug.h) \
    $(wildcard include/config/mp/debug/tool/kdebug.h) \
    $(wildcard include/config/mp/debug/tool/kdebug/debug.h) \
    $(wildcard include/config/mp/debug/tool/changelist/debug.h) \
    $(wildcard include/config/mp/debug/tool/oprofile.h) \
    $(wildcard include/config/mp/debug/tool/oprofile/debug.h) \
    $(wildcard include/config/mp/debug/tool/ubi.h) \
    $(wildcard include/config/mp/debug/tool/ubi/debug.h) \
    $(wildcard include/config/mp/debug/tool/oom.h) \
    $(wildcard include/config/mp/debug/tool/oom/debug.h) \
    $(wildcard include/config/mp/debug/tool/log.h) \
    $(wildcard include/config/mp/debug/tool/log/debug.h) \
    $(wildcard include/config/mp/debug/tool/rm.h) \
    $(wildcard include/config/mp/debug/tool/rm/debug.h) \
    $(wildcard include/config/mp/debug/tool/force/ignored/core/dump/path/bootargs/when/usb/plugin.h) \
    $(wildcard include/config/mp/debug/tool/force/ignored/core/dump/path/bootargs/when/usb/plugin/debug.h) \
    $(wildcard include/config/mp/debug/tool/ramlog.h) \
    $(wildcard include/config/mp/debug/tool/ramlog/debug.h) \
    $(wildcard include/config/mp/debug/tool/rtp/trace.h) \
    $(wildcard include/config/mp/debug/tool/rtp/trace/debug.h) \
    $(wildcard include/config/mp/debug/tool/skip/pulling/running/rt/task.h) \
    $(wildcard include/config/mp/debug/tool/skip/pulling/running/rt/task/debug.h) \
    $(wildcard include/config/mp/debug/tool/interrupt/debug.h) \
    $(wildcard include/config/mp/debug/tool/interrupt/debug/debug.h) \
    $(wildcard include/config/mp/debug/tool/vm/used/size/check.h) \
    $(wildcard include/config/mp/debug/tool/vm/used/size/check/debug.h) \
    $(wildcard include/config/mp/debug/tool/kdebug/devmem.h) \
    $(wildcard include/config/mp/debug/tool/kdebug/devmem/debug.h) \
    $(wildcard include/config/mp/debug/tool/l2.h) \
    $(wildcard include/config/mp/debug/tool/l2/debug.h) \
    $(wildcard include/config/mp/debug/tool/cpu/smp/mask.h) \
    $(wildcard include/config/mp/debug/tool/cpu/smp/mask/debug.h) \
    $(wildcard include/config/mp/remote/control/rc/register/retry.h) \
    $(wildcard include/config/mp/remote/control/rc/register/retry/debug.h) \
    $(wildcard include/config/mp/scsi/mstar/sd/card/hotplug.h) \
    $(wildcard include/config/mp/scsi/mstar/sd/card/hotplug/debug.h) \
    $(wildcard include/config/mp/scsi/mstar/sd/card/immediately/unplug.h) \
    $(wildcard include/config/mp/scsi/mstar/sd/card/immediately/unplug/debug.h) \
    $(wildcard include/config/mp/scsi/hd/suspend.h) \
    $(wildcard include/config/mp/scsi/hd/suspend/debug.h) \
    $(wildcard include/config/mp/scsi/multi/lun.h) \
    $(wildcard include/config/mp/scsi/multi/lun/debug.h) \
    $(wildcard include/config/mp/mm/mali/mmu/notifier.h) \
    $(wildcard include/config/mp/mm/mali/mmu/notifier/debug.h) \
    $(wildcard include/config/mp/mm/cma.h) \
    $(wildcard include/config/mp/mm/cma/debug.h) \
    $(wildcard include/config/mp/mmap/bufferable.h) \
    $(wildcard include/config/mp/mmap/bufferable/debug.h) \
    $(wildcard include/config/mp/mmap/mmap/boundary/protect.h) \
    $(wildcard include/config/mp/mmap/mmap/boundary/protect/debug.h) \
    $(wildcard include/config/mp/miu/mapping.h) \
    $(wildcard include/config/mp/miu/mapping/debug.h) \
    $(wildcard include/config/mp/mips/l2/catch.h) \
    $(wildcard include/config/mp/mips/l2/catch/debug.h) \
    $(wildcard include/config/mp/wdt/off/dbg.h) \
    $(wildcard include/config/mp/wdt/off/dbg/debug.h) \
    $(wildcard include/config/mp/camera/plug/out.h) \
    $(wildcard include/config/mp/camera/plug/out/debug.h) \
    $(wildcard include/config/mp/sysattr/show.h) \
    $(wildcard include/config/mp/sysattr/show/debug.h) \
    $(wildcard include/config/mp/mips/highmem/cache/alias/patch.h) \
    $(wildcard include/config/mp/mips/highmem/cache/alias/patch/debug.h) \
    $(wildcard include/config/mp/mips/highmem/memory/present/patch.h) \
    $(wildcard include/config/mp/mips/highmem/memory/present/patch/debug.h) \
    $(wildcard include/config/mp/mips/dma/dma/alloc/coherent/patch.h) \
    $(wildcard include/config/mp/mips/dma/dma/alloc/coherent/patch/debug.h) \
    $(wildcard include/config/mp/checkpt/boot.h) \
    $(wildcard include/config/mp/checkpt/boot/debug.h) \
    $(wildcard include/config/mp/webcam/init.h) \
    $(wildcard include/config/mp/webcam/init/debug.h) \
    $(wildcard include/config/mp/mips/mips16e/unaligned/access.h) \
    $(wildcard include/config/mp/mips/mips16e/unaligned/access/debug.h) \
    $(wildcard include/config/mp/mips/mips16e/unaligned/access/fpu/fixed.h) \
    $(wildcard include/config/mp/mips/mips16e/unaligned/access/fpu/fixed/debug.h) \
    $(wildcard include/config/mp/ntfs3g/wrap.h) \
    $(wildcard include/config/mp/ntfs3g/wrap/debug.h) \
    $(wildcard include/config/mp/bdi/mstar/bdi/patch.h) \
    $(wildcard include/config/mp/bdi/mstar/bdi/patch/debug.h) \
    $(wildcard include/config/mp/smp/startup.h) \
    $(wildcard include/config/mp/smp/startup/debug.h) \
    $(wildcard include/config/mp/uart/serial/8250/riu/base.h) \
    $(wildcard include/config/mp/uart/serial/8250/riu/base/debug.h) \
    $(wildcard include/config/mp/uart/serial/open/set/baudrate.h) \
    $(wildcard include/config/mp/uart/serial/open/set/baudrate/debug.h) \
    $(wildcard include/config/mp/ntfs/ioctl.h) \
    $(wildcard include/config/mp/ntfs/ioctl/debug.h) \
    $(wildcard include/config/mp/ntfs/volume/label.h) \
    $(wildcard include/config/mp/ntfs/volume/label/debug.h) \
    $(wildcard include/config/mp/ntfs/volume/id.h) \
    $(wildcard include/config/mp/ntfs/volume/id/debug.h) \
    $(wildcard include/config/mp/ntfs/read/pages.h) \
    $(wildcard include/config/mp/ntfs/read/pages/debug.h) \
    $(wildcard include/config/mp/ntfs/refine/readdir.h) \
    $(wildcard include/config/mp/ntfs/refine/readdir/debug.h) \
    $(wildcard include/config/mp/ntfs/2tb/plus/partition/support.h) \
    $(wildcard include/config/mp/ntfs/2tb/plus/partition/support/debug.h) \
    $(wildcard include/config/mp/ntfs/page/cache/readahead.h) \
    $(wildcard include/config/mp/ntfs/page/cache/readahead/debug.h) \
    $(wildcard include/config/mp/kernel/compat/from/2/6/35/11/to/3/1/10.h) \
    $(wildcard include/config/mp/kernel/compat/from/2/6/35/11/to/3/1/10/debug.h) \
    $(wildcard include/config/mp/kernel/compat/patch/fix/inode/cluster/list.h) \
    $(wildcard include/config/mp/kernel/compat/patch/fix/inode/cluster/list/debug.h) \
    $(wildcard include/config/mp/ethernet/mstar/icmp/enhance.h) \
    $(wildcard include/config/mp/ethernet/mstar/icmp/enhance/debug.h) \
    $(wildcard include/config/mp/usb/str/patch.h) \
    $(wildcard include/config/mp/usb/str/patch/debug.h) \
    $(wildcard include/config/mp/fat/volume/label.h) \
    $(wildcard include/config/mp/fat/volume/label/debug.h) \
    $(wildcard include/config/mp/fat/fallocate.h) \
    $(wildcard include/config/mp/fat/fallocate/debug.h) \
    $(wildcard include/config/mp/ca7/quad/core/patch.h) \
    $(wildcard include/config/mp/ca7/quad/core/patch/debug.h) \
    $(wildcard include/config/mp/ca7/hw/break/points/enable.h) \
    $(wildcard include/config/mp/ca7/hw/break/points/enable/debug.h) \
    $(wildcard include/config/mp/ca7/performance/monitor/enable.h) \
    $(wildcard include/config/mp/ca7/performance/monitor/enable/debug.h) \
    $(wildcard include/config/mp/kernel/bug/patch/remove.h) \
    $(wildcard include/config/mp/kernel/bug/patch/remove/debug.h) \
    $(wildcard include/config/mp/hid/hidraw/patch.h) \
    $(wildcard include/config/mp/hid/hidraw/patch/debug.h) \
    $(wildcard include/config/mp/hid/hidraw/opensrc.h) \
    $(wildcard include/config/mp/hid/hidraw/opensrc/debug.h) \
    $(wildcard include/config/mp/hid/hidraw/trylock.h) \
    $(wildcard include/config/mp/hid/hidraw/trylock/debug.h) \
    $(wildcard include/config/mp/blcr/compile/patch.h) \
    $(wildcard include/config/mp/blcr/compile/patch/debug.h) \
    $(wildcard include/config/mp/wireless/mstar.h) \
    $(wildcard include/config/mp/wireless/mstar/debug.h) \
    $(wildcard include/config/mp/sata/ata/core/patch.h) \
    $(wildcard include/config/mp/sata/ata/core/patch/debug.h) \
    $(wildcard include/config/mp/acp/l2.h) \
    $(wildcard include/config/mp/acp/l2/debug.h) \
    $(wildcard include/config/mp/acp/direction.h) \
    $(wildcard include/config/mp/acp/direction/debug.h) \
    $(wildcard include/config/mp/temp/debug/eiffel/netflix.h) \
    $(wildcard include/config/mp/temp/debug/eiffel/netflix/debug.h) \
    $(wildcard include/config/mp/schd/debug/re/sched/nor.h) \
    $(wildcard include/config/mp/schd/debug/re/sched/nor/debug.h) \
    $(wildcard include/config/mp/cache/drop.h) \
    $(wildcard include/config/mp/cache/drop/debug.h) \
    $(wildcard include/config/mp/udc/mstar.h) \
    $(wildcard include/config/mp/udc/mstar/debug.h) \
    $(wildcard include/config/mp/mm/dma/zone/extend/patch.h) \
    $(wildcard include/config/mp/mm/dma/zone/extend/patch/debug.h) \
    $(wildcard include/config/mp/antutu/benchmark/rise/performance.h) \
    $(wildcard include/config/mp/antutu/benchmark/rise/performance/debug.h) \
    $(wildcard include/config/mp/android/cts/nativecodetest.h) \
    $(wildcard include/config/mp/android/cts/nativecodetest/debug.h) \
    $(wildcard include/config/mp/ipv6/ping/socket.h) \
    $(wildcard include/config/mp/ipv6/ping/socket/debug.h) \
    $(wildcard include/config/mp/sparse/mem/enable/holes/in/zone/check.h) \
    $(wildcard include/config/mp/sparse/mem/enable/holes/in/zone/check/debug.h) \
    $(wildcard include/config/mp/fusion/size/address/refine.h) \
    $(wildcard include/config/mp/fusion/size/address/refine/debug.h) \
    $(wildcard include/config/mp/mm/alloc/change/kzalloc/to/vzalloc.h) \
    $(wildcard include/config/mp/mm/alloc/change/kzalloc/to/vzalloc/debug.h) \
    $(wildcard include/config/mp/sched/policy/patch.h) \
    $(wildcard include/config/mp/sched/policy/patch/debug.h) \
  include/linux/linkage.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/linkage.h \
  include/linux/wait.h \
    $(wildcard include/config/lockdep.h) \
  include/linux/list.h \
    $(wildcard include/config/debug/list.h) \
  include/linux/poison.h \
    $(wildcard include/config/illegal/pointer/value.h) \
  include/linux/const.h \
  include/linux/spinlock.h \
    $(wildcard include/config/debug/spinlock.h) \
    $(wildcard include/config/generic/lockbreak.h) \
  include/linux/typecheck.h \
  include/linux/preempt.h \
    $(wildcard include/config/debug/preempt.h) \
    $(wildcard include/config/preempt/tracer.h) \
    $(wildcard include/config/preempt/count.h) \
    $(wildcard include/config/preempt/notifiers.h) \
  include/linux/thread_info.h \
    $(wildcard include/config/compat.h) \
  include/linux/bitops.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/bitops.h \
    $(wildcard include/config/cpu/mipsr2.h) \
  include/linux/irqflags.h \
    $(wildcard include/config/trace/irqflags.h) \
    $(wildcard include/config/irqsoff/tracer.h) \
    $(wildcard include/config/trace/irqflags/support.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/irqflags.h \
    $(wildcard include/config/mips/mt/smtc.h) \
    $(wildcard include/config/irq/cpu.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/hazards.h \
    $(wildcard include/config/cpu/cavium/octeon.h) \
    $(wildcard include/config/cpu/mipsr1.h) \
    $(wildcard include/config/mips/alchemy.h) \
    $(wildcard include/config/cpu/loongson2.h) \
    $(wildcard include/config/cpu/r10000.h) \
    $(wildcard include/config/cpu/r5500.h) \
    $(wildcard include/config/cpu/rm9000.h) \
    $(wildcard include/config/cpu/sb1.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/cpu-features.h \
    $(wildcard include/config/32bit.h) \
    $(wildcard include/config/cpu/mipsr2/irq/vi.h) \
    $(wildcard include/config/cpu/mipsr2/irq/ei.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/cpu.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/cpu-info.h \
    $(wildcard include/config/mips/mt/smp.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/cache.h \
    $(wildcard include/config/mips/l1/cache/shift.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-generic/kmalloc.h \
    $(wildcard include/config/dma/coherent.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-malta/cpu-feature-overrides.h \
    $(wildcard include/config/cpu/mips32.h) \
    $(wildcard include/config/cpu/mips64.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/barrier.h \
    $(wildcard include/config/cpu/has/sync.h) \
    $(wildcard include/config/sgi/ip28.h) \
    $(wildcard include/config/cpu/has/wb.h) \
    $(wildcard include/config/weak/ordering.h) \
    $(wildcard include/config/weak/reordering/beyond/llsc.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/bug.h \
    $(wildcard include/config/bug.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/break.h \
  include/asm-generic/bug.h \
    $(wildcard include/config/generic/bug.h) \
    $(wildcard include/config/generic/bug/relative/pointers.h) \
    $(wildcard include/config/debug/bugverbose.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/byteorder.h \
  include/linux/byteorder/little_endian.h \
  include/linux/swab.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/swab.h \
  include/linux/byteorder/generic.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/war.h \
    $(wildcard include/config/cpu/r4000/workarounds.h) \
    $(wildcard include/config/cpu/r4400/workarounds.h) \
    $(wildcard include/config/cpu/daddi/workarounds.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-malta/war.h \
  include/asm-generic/bitops/non-atomic.h \
  include/asm-generic/bitops/fls64.h \
  include/asm-generic/bitops/ffz.h \
  include/asm-generic/bitops/find.h \
    $(wildcard include/config/generic/find/first/bit.h) \
  include/asm-generic/bitops/sched.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/arch_hweight.h \
  include/asm-generic/bitops/arch_hweight.h \
  include/asm-generic/bitops/const_hweight.h \
  include/asm-generic/bitops/le.h \
  include/asm-generic/bitops/ext2-atomic.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/thread_info.h \
    $(wildcard include/config/page/size/4kb.h) \
    $(wildcard include/config/page/size/8kb.h) \
    $(wildcard include/config/page/size/16kb.h) \
    $(wildcard include/config/page/size/32kb.h) \
    $(wildcard include/config/page/size/64kb.h) \
    $(wildcard include/config/debug/stack/usage.h) \
    $(wildcard include/config/mips32/o32.h) \
    $(wildcard include/config/mips32/n32.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/processor.h \
    $(wildcard include/config/cavium/octeon/cvmseg/size.h) \
    $(wildcard include/config/mips/mt/fpaff.h) \
    $(wildcard include/config/cpu/has/prefetch.h) \
  include/linux/cpumask.h \
    $(wildcard include/config/cpumask/offstack.h) \
    $(wildcard include/config/hotplug/cpu.h) \
    $(wildcard include/config/debug/per/cpu/maps.h) \
    $(wildcard include/config/disable/obsolete/cpumask/functions.h) \
  include/linux/kernel.h \
    $(wildcard include/config/preempt/voluntary.h) \
    $(wildcard include/config/debug/atomic/sleep.h) \
    $(wildcard include/config/prove/locking.h) \
    $(wildcard include/config/ring/buffer.h) \
    $(wildcard include/config/tracing.h) \
    $(wildcard include/config/numa.h) \
    $(wildcard include/config/compaction.h) \
    $(wildcard include/config/ftrace/mcount/record.h) \
  /home/Supernova/mips-4.3/bin/../lib/gcc/mips-linux-gnu/4.3.2/include/stdarg.h \
  include/linux/log2.h \
    $(wildcard include/config/arch/has/ilog2/u32.h) \
    $(wildcard include/config/arch/has/ilog2/u64.h) \
  include/linux/printk.h \
    $(wildcard include/config/printk.h) \
    $(wildcard include/config/dynamic/debug.h) \
  include/linux/init.h \
    $(wildcard include/config/modules.h) \
    $(wildcard include/config/hotplug.h) \
  include/linux/dynamic_debug.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/div64.h \
  include/asm-generic/div64.h \
  include/linux/threads.h \
    $(wildcard include/config/nr/cpus.h) \
    $(wildcard include/config/base/small.h) \
  include/linux/bitmap.h \
  include/linux/string.h \
    $(wildcard include/config/binary/printf.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/string.h \
    $(wildcard include/config/cpu/r3000.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/cachectl.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mipsregs.h \
    $(wildcard include/config/cpu/vr41xx.h) \
    $(wildcard include/config/hugetlb/page.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/prefetch.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/system.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/addrspace.h \
    $(wildcard include/config/cpu/r8000.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-generic/spaces.h \
    $(wildcard include/config/mstar/mips.h) \
    $(wildcard include/config/dma/noncoherent.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/cmpxchg.h \
  include/asm-generic/cmpxchg-local.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/dsp.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/watch.h \
    $(wildcard include/config/hardware/watchpoints.h) \
  include/linux/stringify.h \
  include/linux/bottom_half.h \
  include/linux/spinlock_types.h \
  include/linux/spinlock_types_up.h \
  include/linux/lockdep.h \
    $(wildcard include/config/lock/stat.h) \
    $(wildcard include/config/prove/rcu.h) \
  include/linux/rwlock_types.h \
  include/linux/spinlock_up.h \
  include/linux/rwlock.h \
  include/linux/spinlock_api_up.h \
  include/linux/atomic.h \
    $(wildcard include/config/arch/has/atomic/or.h) \
    $(wildcard include/config/generic/atomic64.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/atomic.h \
  include/asm-generic/atomic-long.h \
  include/asm-generic/atomic64.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/current.h \
  include/asm-generic/current.h \
  include/linux/kdev_t.h \
  include/linux/dcache.h \
  include/linux/rculist.h \
  include/linux/rcupdate.h \
    $(wildcard include/config/rcu/torture/test.h) \
    $(wildcard include/config/tree/rcu.h) \
    $(wildcard include/config/tree/preempt/rcu.h) \
    $(wildcard include/config/preempt/rcu.h) \
    $(wildcard include/config/no/hz.h) \
    $(wildcard include/config/tiny/rcu.h) \
    $(wildcard include/config/tiny/preempt/rcu.h) \
    $(wildcard include/config/debug/objects/rcu/head.h) \
    $(wildcard include/config/preempt/rt.h) \
  include/linux/cache.h \
    $(wildcard include/config/arch/has/cache/line/size.h) \
  include/linux/seqlock.h \
  include/linux/completion.h \
  include/linux/debugobjects.h \
    $(wildcard include/config/debug/objects.h) \
    $(wildcard include/config/debug/objects/free.h) \
  include/linux/rcutree.h \
  include/linux/rculist_bl.h \
  include/linux/list_bl.h \
  include/linux/bit_spinlock.h \
  include/linux/path.h \
  include/linux/stat.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/stat.h \
  include/linux/time.h \
    $(wildcard include/config/arch/uses/gettimeoffset.h) \
  include/linux/math64.h \
  include/linux/radix-tree.h \
  include/linux/prio_tree.h \
  include/linux/pid.h \
  include/linux/mutex.h \
    $(wildcard include/config/debug/mutexes.h) \
    $(wildcard include/config/have/arch/mutex/cpu/relax.h) \
  include/linux/capability.h \
  include/linux/semaphore.h \
  include/linux/fiemap.h \
  include/linux/shrinker.h \
  include/linux/quota.h \
    $(wildcard include/config/quota/netlink/interface.h) \
  include/linux/errno.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/errno.h \
  include/asm-generic/errno-base.h \
  include/linux/rwsem.h \
    $(wildcard include/config/rwsem/generic/spinlock.h) \
  include/linux/rwsem-spinlock.h \
  include/linux/percpu_counter.h \
  include/linux/smp.h \
    $(wildcard include/config/use/generic/smp/helpers.h) \
  include/linux/percpu.h \
    $(wildcard include/config/need/per/cpu/embed/first/chunk.h) \
    $(wildcard include/config/need/per/cpu/page/first/chunk.h) \
    $(wildcard include/config/have/setup/per/cpu/area.h) \
  include/linux/pfn.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/percpu.h \
  include/asm-generic/percpu.h \
  include/linux/percpu-defs.h \
    $(wildcard include/config/debug/force/weak/per/cpu.h) \
  include/linux/dqblk_xfs.h \
  include/linux/dqblk_v1.h \
  include/linux/dqblk_v2.h \
  include/linux/dqblk_qtree.h \
  include/linux/nfs_fs_i.h \
  include/linux/nfs.h \
  include/linux/sunrpc/msg_prot.h \
  include/linux/inet.h \
  include/linux/fcntl.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/fcntl.h \
  include/asm-generic/fcntl.h \
  include/linux/err.h \
  include/linux/hugetlb.h \
    $(wildcard include/config/hugetlbfs.h) \
    $(wildcard include/config/highmem.h) \
  include/linux/mm_types.h \
    $(wildcard include/config/split/ptlock/cpus.h) \
    $(wildcard include/config/want/page/debug/flags.h) \
    $(wildcard include/config/kmemcheck.h) \
    $(wildcard include/config/slub.h) \
    $(wildcard include/config/cmpxchg/local.h) \
    $(wildcard include/config/mmu.h) \
    $(wildcard include/config/aio.h) \
    $(wildcard include/config/mm/owner.h) \
    $(wildcard include/config/transparent/hugepage.h) \
  include/linux/auxvec.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/auxvec.h \
  include/linux/rbtree.h \
  include/linux/page-debug-flags.h \
    $(wildcard include/config/page/poisoning.h) \
    $(wildcard include/config/page/debug/something/else.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/page.h \
    $(wildcard include/config/flatmem.h) \
    $(wildcard include/config/sparsemem.h) \
    $(wildcard include/config/need/multiple/nodes.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/io.h \
  include/asm-generic/iomap.h \
    $(wildcard include/config/has/ioport.h) \
    $(wildcard include/config/pci.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/pgtable-bits.h \
    $(wildcard include/config/cpu/tx39xx.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-generic/ioremap.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-generic/mangle-port.h \
    $(wildcard include/config/swap/io/space.h) \
  include/asm-generic/memory_model.h \
    $(wildcard include/config/discontigmem.h) \
    $(wildcard include/config/sparsemem/vmemmap.h) \
  include/asm-generic/getorder.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mmu.h \
  include/linux/hugetlb_inline.h \
  include/linux/mm.h \
    $(wildcard include/config/sysctl.h) \
    $(wildcard include/config/stack/growsup.h) \
    $(wildcard include/config/ia64.h) \
    $(wildcard include/config/ksm.h) \
    $(wildcard include/config/arch/populates/node/map.h) \
    $(wildcard include/config/have/arch/early/pfn/to/nid.h) \
    $(wildcard include/config/proc/fs.h) \
    $(wildcard include/config/debug/pagealloc.h) \
    $(wildcard include/config/hibernation.h) \
  include/linux/gfp.h \
    $(wildcard include/config/zone/dma.h) \
    $(wildcard include/config/zone/dma32.h) \
  include/linux/mmzone.h \
    $(wildcard include/config/force/max/zoneorder.h) \
    $(wildcard include/config/memory/hotplug.h) \
    $(wildcard include/config/flat/node/mem/map.h) \
    $(wildcard include/config/cgroup/mem/res/ctlr.h) \
    $(wildcard include/config/no/bootmem.h) \
    $(wildcard include/config/have/memory/present.h) \
    $(wildcard include/config/have/memoryless/nodes.h) \
    $(wildcard include/config/need/node/memmap/size.h) \
    $(wildcard include/config/sparsemem/extreme.h) \
    $(wildcard include/config/have/arch/pfn/valid.h) \
    $(wildcard include/config/nodes/span/other/nodes.h) \
    $(wildcard include/config/holes/in/zone.h) \
    $(wildcard include/config/arch/has/holes/memorymodel.h) \
  include/linux/numa.h \
    $(wildcard include/config/nodes/shift.h) \
  include/linux/nodemask.h \
  include/linux/pageblock-flags.h \
    $(wildcard include/config/hugetlb/page/size/variable.h) \
  include/generated/bounds.h \
  include/linux/memory_hotplug.h \
    $(wildcard include/config/memory/hotremove.h) \
    $(wildcard include/config/have/arch/nodedata/extension.h) \
  include/linux/notifier.h \
  include/linux/srcu.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/sparsemem.h \
  include/linux/topology.h \
    $(wildcard include/config/sched/smt.h) \
    $(wildcard include/config/sched/mc.h) \
    $(wildcard include/config/sched/book.h) \
    $(wildcard include/config/use/percpu/numa/node/id.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/topology.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mach-generic/topology.h \
  include/asm-generic/topology.h \
  include/linux/mmdebug.h \
    $(wildcard include/config/debug/vm.h) \
    $(wildcard include/config/debug/virtual.h) \
  include/linux/debug_locks.h \
    $(wildcard include/config/debug/locking/api/selftests.h) \
  include/linux/range.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/pgtable.h \
    $(wildcard include/config/cpu/supports/uncached/accelerated.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/pgtable-32.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/fixmap.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/kmap_types.h \
    $(wildcard include/config/debug/highmem.h) \
  include/asm-generic/kmap_types.h \
  include/asm-generic/pgtable-nopmd.h \
  include/asm-generic/pgtable-nopud.h \
  include/asm-generic/pgtable.h \
  include/linux/page-flags.h \
    $(wildcard include/config/pageflags/extended.h) \
    $(wildcard include/config/arch/uses/pg/uncached.h) \
    $(wildcard include/config/memory/failure.h) \
    $(wildcard include/config/swap.h) \
    $(wildcard include/config/s390.h) \
  include/linux/huge_mm.h \
  include/linux/vmstat.h \
    $(wildcard include/config/vm/event/counters.h) \
  include/linux/vm_event_item.h \
  include/linux/mman.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/mman.h \
  include/linux/proc_fs.h \
    $(wildcard include/config/proc/devicetree.h) \
    $(wildcard include/config/proc/kcore.h) \
  include/linux/slab.h \
    $(wildcard include/config/slab/debug.h) \
    $(wildcard include/config/failslab.h) \
    $(wildcard include/config/slob.h) \
    $(wildcard include/config/debug/slab.h) \
    $(wildcard include/config/slab.h) \
  include/linux/slub_def.h \
    $(wildcard include/config/slub/stats.h) \
    $(wildcard include/config/slub/debug.h) \
  include/linux/workqueue.h \
    $(wildcard include/config/debug/objects/work.h) \
    $(wildcard include/config/freezer.h) \
  include/linux/timer.h \
    $(wildcard include/config/timer/stats.h) \
    $(wildcard include/config/debug/objects/timers.h) \
  include/linux/ktime.h \
    $(wildcard include/config/ktime/scalar.h) \
  include/linux/jiffies.h \
  include/linux/timex.h \
  include/linux/param.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/param.h \
  include/asm-generic/param.h \
    $(wildcard include/config/hz.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/timex.h \
  include/linux/kobject.h \
  include/linux/sysfs.h \
  include/linux/kobject_ns.h \
  include/linux/kref.h \
  include/linux/kmemleak.h \
    $(wildcard include/config/debug/kmemleak.h) \
  include/linux/magic.h \
  include/linux/quicklist.h \
    $(wildcard include/config/quicklist.h) \
    $(wildcard include/config/nr/quick.h) \
  include/linux/seq_file.h \
  include/linux/swap.h \
    $(wildcard include/config/cgroup/mem/res/ctlr/swap.h) \
  include/linux/memcontrol.h \
    $(wildcard include/config/cgroup/mem/cont.h) \
  include/linux/cgroup.h \
    $(wildcard include/config/cgroups.h) \
  include/linux/sched.h \
    $(wildcard include/config/sched/debug.h) \
    $(wildcard include/config/show/fault/trace/info.h) \
    $(wildcard include/config/arm.h) \
    $(wildcard include/config/mips.h) \
    $(wildcard include/config/lockup/detector.h) \
    $(wildcard include/config/detect/hung/task.h) \
    $(wildcard include/config/core/dump/default/elf/headers.h) \
    $(wildcard include/config/sched/autogroup.h) \
    $(wildcard include/config/virt/cpu/accounting.h) \
    $(wildcard include/config/bsd/process/acct.h) \
    $(wildcard include/config/taskstats.h) \
    $(wildcard include/config/audit.h) \
    $(wildcard include/config/inotify/user.h) \
    $(wildcard include/config/fanotify.h) \
    $(wildcard include/config/posix/mqueue.h) \
    $(wildcard include/config/keys.h) \
    $(wildcard include/config/perf/events.h) \
    $(wildcard include/config/schedstats.h) \
    $(wildcard include/config/task/delay/acct.h) \
    $(wildcard include/config/fair/group/sched.h) \
    $(wildcard include/config/rt/group/sched.h) \
    $(wildcard include/config/blk/dev/io/trace.h) \
    $(wildcard include/config/rcu/boost.h) \
    $(wildcard include/config/compat/brk.h) \
    $(wildcard include/config/cc/stackprotector.h) \
    $(wildcard include/config/sysvipc.h) \
    $(wildcard include/config/generic/hardirqs.h) \
    $(wildcard include/config/rt/mutexes.h) \
    $(wildcard include/config/task/xacct.h) \
    $(wildcard include/config/cpusets.h) \
    $(wildcard include/config/futex.h) \
    $(wildcard include/config/fault/injection.h) \
    $(wildcard include/config/latencytop.h) \
    $(wildcard include/config/function/graph/tracer.h) \
    $(wildcard include/config/have/hw/breakpoint.h) \
    $(wildcard include/config/have/unstable/sched/clock.h) \
    $(wildcard include/config/irq/time/accounting.h) \
    $(wildcard include/config/cgroup/sched.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/ptrace.h \
    $(wildcard include/config/cpu/has/smartmips.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/isadep.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/cputime.h \
  include/asm-generic/cputime.h \
  include/linux/sem.h \
  include/linux/ipc.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/ipcbuf.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/sembuf.h \
  include/linux/signal.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/signal.h \
    $(wildcard include/config/trad/signals.h) \
  include/asm-generic/signal-defs.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/sigcontext.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/siginfo.h \
  include/asm-generic/siginfo.h \
  include/linux/proportions.h \
  include/linux/seccomp.h \
    $(wildcard include/config/seccomp.h) \
  include/linux/rtmutex.h \
    $(wildcard include/config/debug/rt/mutexes.h) \
  include/linux/plist.h \
    $(wildcard include/config/debug/pi/list.h) \
  include/linux/resource.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/resource.h \
  include/asm-generic/resource.h \
  include/linux/hrtimer.h \
    $(wildcard include/config/high/res/timers.h) \
    $(wildcard include/config/timerfd.h) \
  include/linux/timerqueue.h \
  include/linux/task_io_accounting.h \
    $(wildcard include/config/task/io/accounting.h) \
  include/linux/latencytop.h \
  include/linux/cred.h \
    $(wildcard include/config/debug/credentials.h) \
    $(wildcard include/config/user/ns.h) \
  include/linux/key.h \
  include/linux/sysctl.h \
  include/linux/selinux.h \
    $(wildcard include/config/security/selinux.h) \
  include/linux/aio.h \
  include/linux/aio_abi.h \
  include/linux/uio.h \
  include/linux/cgroupstats.h \
  include/linux/taskstats.h \
  include/linux/prio_heap.h \
  include/linux/idr.h \
  include/linux/cgroup_subsys.h \
    $(wildcard include/config/cgroup/debug.h) \
    $(wildcard include/config/cgroup/cpuacct.h) \
    $(wildcard include/config/cgroup/device.h) \
    $(wildcard include/config/cgroup/freezer.h) \
    $(wildcard include/config/net/cls/cgroup.h) \
    $(wildcard include/config/blk/cgroup.h) \
    $(wildcard include/config/cgroup/perf.h) \
  include/linux/node.h \
    $(wildcard include/config/memory/hotplug/sparse.h) \
  include/linux/sysdev.h \
  include/linux/module.h \
    $(wildcard include/config/unused/symbols.h) \
    $(wildcard include/config/kallsyms.h) \
    $(wildcard include/config/tracepoints.h) \
    $(wildcard include/config/event/tracing.h) \
    $(wildcard include/config/module/unload.h) \
    $(wildcard include/config/constructors.h) \
    $(wildcard include/config/debug/set/module/ronx.h) \
  include/linux/kmod.h \
  include/linux/elf.h \
  include/linux/elf-em.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/elf.h \
    $(wildcard include/config/mips32/compat.h) \
  include/linux/moduleparam.h \
    $(wildcard include/config/alpha.h) \
    $(wildcard include/config/ppc64.h) \
  include/linux/tracepoint.h \
  include/linux/jump_label.h \
    $(wildcard include/config/jump/label.h) \
  include/linux/export.h \
    $(wildcard include/config/symbol/prefix.h) \
    $(wildcard include/config/modversions.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/module.h \
    $(wildcard include/config/cpu/mips32/r1.h) \
    $(wildcard include/config/cpu/mips32/r2.h) \
    $(wildcard include/config/cpu/mips64/r1.h) \
    $(wildcard include/config/cpu/mips64/r2.h) \
    $(wildcard include/config/cpu/r4300.h) \
    $(wildcard include/config/cpu/r4x00.h) \
    $(wildcard include/config/cpu/tx49xx.h) \
    $(wildcard include/config/cpu/r5000.h) \
    $(wildcard include/config/cpu/r5432.h) \
    $(wildcard include/config/cpu/r6000.h) \
    $(wildcard include/config/cpu/nevada.h) \
    $(wildcard include/config/cpu/rm7000.h) \
    $(wildcard include/config/cpu/xlr.h) \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/uaccess.h \
  include/trace/events/module.h \
  include/trace/define_trace.h \
  include/linux/pm.h \
    $(wildcard include/config/pm.h) \
    $(wildcard include/config/pm/sleep.h) \
    $(wildcard include/config/pm/runtime.h) \
  include/linux/uaccess.h \
  include/linux/ctype.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/setup.h \
  /home/ffs/msd3563/kernel/Kernel/RedLion/3.1.10/arch/mips/include/asm/cacheflush.h \

drivers/mstar/pm_addr/mdrv_pm_proc.o: $(deps_drivers/mstar/pm_addr/mdrv_pm_proc.o)

$(deps_drivers/mstar/pm_addr/mdrv_pm_proc.o):

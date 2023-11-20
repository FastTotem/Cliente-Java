package util;

import java.util.concurrent.TimeUnit;

public class Conversor {
    private static final long KIBI = 1L << 10;
    private static final long MEBI = 1L << 20;
    private static final long GIBI = 1L << 30;
    private static final long TEBI = 1L << 40;
    private static final long PEBI = 1L << 50;
    private static final long EXBI = 1L << 60;

    public static Double formatarBytes(long bytes) {
        if (bytes < MEBI) { // KiB
            return formatarUnidades(bytes, KIBI);
        } else if (bytes < GIBI) { // MiB
            return formatarUnidades(bytes, MEBI);
        } else if (bytes < TEBI) { // GiB
            return formatarUnidades(bytes, GIBI);
        } else if (bytes < PEBI) { // TiB
            return formatarUnidades(bytes, TEBI);
        } else if (bytes < EXBI) { // PiB
            return formatarUnidades(bytes, PEBI);
        } else { // EiB
            return formatarUnidades(bytes, EXBI);
        }
    }

    private static Double formatarUnidades(long valor, long prefixo) {
        if (valor % prefixo == 0) {
            return (double) valor / prefixo;
        }
        return (double) valor / prefixo;
    }
    public static String formatarSegundosDecorridos(long secs) {
        long days = TimeUnit.SECONDS.toDays(secs);
        long eTime = secs - TimeUnit.DAYS.toSeconds(days);
        long hr = TimeUnit.SECONDS.toHours(eTime);
        eTime -= TimeUnit.HOURS.toSeconds(hr);
        long min = TimeUnit.SECONDS.toMinutes(eTime);
        eTime -= TimeUnit.MINUTES.toSeconds(min);
        return String.format("%d days, %02d:%02d:%02d", days, hr, min, eTime);
    }
}

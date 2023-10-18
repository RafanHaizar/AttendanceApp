package kotlinx.coroutines.channels;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(mo112d1 = {"\u0000 \u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aJ\u0010\u0000\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\u00072\u001a\u0010\b\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\n0\t\"\u0006\u0012\u0002\b\u00030\nH\u0001¢\u0006\u0002\u0010\u000b\u001a!\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a1\u0010\u0010\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\u0007*\u0006\u0012\u0002\b\u00030\nH\u0001\u001a!\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0007\u001aZ\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010\u0018\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a0\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001aT\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a)\u0010!\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\"\u001a\u00020\u0012H@ø\u0001\u0000¢\u0006\u0002\u0010#\u001a+\u0010$\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\"\u001a\u00020\u0012H@ø\u0001\u0000¢\u0006\u0002\u0010#\u001aT\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ai\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001727\u0010 \u001a3\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u001aT\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a$\u0010*\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\b\b\u0000\u0010\u000e*\u00020\u001b*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\nH\u0001\u001aA\u0010+\u001a\u0002H,\"\b\b\u0000\u0010\u000e*\u00020\u001b\"\u0010\b\u0001\u0010,*\n\u0012\u0006\b\u0000\u0012\u0002H\u000e0-*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\n2\u0006\u0010.\u001a\u0002H,H@ø\u0001\u0000¢\u0006\u0002\u0010/\u001a?\u0010+\u001a\u0002H,\"\b\b\u0000\u0010\u000e*\u00020\u001b\"\u000e\b\u0001\u0010,*\b\u0012\u0004\u0012\u0002H\u000e00*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\n2\u0006\u0010.\u001a\u0002H,H@ø\u0001\u0000¢\u0006\u0002\u00101\u001a!\u00102\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a#\u00103\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a`\u00104\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172(\u00106\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\n0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a)\u00107\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u00108\u001a\u0002H\u000eH@ø\u0001\u0000¢\u0006\u0002\u00109\u001a!\u0010:\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a)\u0010;\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u00108\u001a\u0002H\u000eH@ø\u0001\u0000¢\u0006\u0002\u00109\u001a#\u0010<\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001aZ\u0010=\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u00106\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ao\u0010>\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001727\u00106\u001a3\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0001ø\u0001\u0000¢\u0006\u0002\u0010(\u001au\u0010?\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\b\b\u0001\u00105*\u00020\u001b*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001729\u00106\u001a5\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u001a`\u0010@\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\b\b\u0001\u00105*\u00020\u001b*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172$\u00106\u001a \b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a?\u0010A\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u001a\u0010B\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0Cj\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`DH@ø\u0001\u0000¢\u0006\u0002\u0010E\u001a?\u0010F\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u001a\u0010B\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0Cj\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`DH@ø\u0001\u0000¢\u0006\u0002\u0010E\u001a!\u0010G\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a$\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\b\b\u0000\u0010\u000e*\u00020\u001b*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\nH\u0007\u001a!\u0010I\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a#\u0010J\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a0\u0010K\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001aT\u0010L\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a9\u0010M\u001a\u0002H,\"\u0004\b\u0000\u0010\u000e\"\u000e\b\u0001\u0010,*\b\u0012\u0004\u0012\u0002H\u000e00*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010.\u001a\u0002H,H@ø\u0001\u0000¢\u0006\u0002\u00101\u001a;\u0010N\u001a\u0002H,\"\u0004\b\u0000\u0010\u000e\"\u0010\b\u0001\u0010,*\n\u0012\u0006\b\u0000\u0012\u0002H\u000e0-*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010.\u001a\u0002H,H@ø\u0001\u0000¢\u0006\u0002\u0010/\u001a?\u0010O\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0P\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010Q*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0R0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001aU\u0010O\u001a\u0002HS\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010Q\"\u0018\b\u0002\u0010S*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0015\u0012\u0006\b\u0000\u0012\u0002HQ0T*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0R0\n2\u0006\u0010.\u001a\u0002HSH@ø\u0001\u0000¢\u0006\u0002\u0010U\u001a'\u0010V\u001a\b\u0012\u0004\u0012\u0002H\u000e0W\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a'\u0010X\u001a\b\u0012\u0004\u0012\u0002H\u000e0Y\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a'\u0010Z\u001a\b\u0012\u0004\u0012\u0002H\u000e0[\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a.\u0010\\\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0]0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001a?\u0010^\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H50R0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\f\u0010_\u001a\b\u0012\u0004\u0012\u0002H50\nH\u0004\u001az\u0010^\u001a\b\u0012\u0004\u0012\u0002HQ0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105\"\u0004\b\u0002\u0010Q*\b\u0012\u0004\u0012\u0002H\u000e0\n2\f\u0010_\u001a\b\u0012\u0004\u0012\u0002H50\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001726\u00106\u001a2\u0012\u0013\u0012\u0011H\u000e¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(`\u0012\u0013\u0012\u0011H5¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(a\u0012\u0004\u0012\u0002HQ0\u0019H\u0001\u0002\u0004\n\u0002\b\u0019¨\u0006b"}, mo113d2 = {"consumesAll", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "channels", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "([Lkotlinx/coroutines/channels/ReceiveChannel;)Lkotlin/jvm/functions/Function1;", "any", "", "E", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "consumes", "count", "", "distinct", "distinctBy", "K", "context", "Lkotlin/coroutines/CoroutineContext;", "selector", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "drop", "n", "dropWhile", "predicate", "elementAt", "index", "(Lkotlinx/coroutines/channels/ReceiveChannel;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "elementAtOrNull", "filter", "filterIndexed", "Lkotlin/Function3;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/channels/ReceiveChannel;", "filterNot", "filterNotNull", "filterNotNullTo", "C", "", "destination", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/SendChannel;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "first", "firstOrNull", "flatMap", "R", "transform", "indexOf", "element", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "last", "lastIndexOf", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "mapNotNull", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Comparator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "minWith", "none", "requireNoNulls", "single", "singleOrNull", "take", "takeWhile", "toChannel", "toCollection", "toMap", "", "V", "Lkotlin/Pair;", "M", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toMutableList", "", "toMutableSet", "", "toSet", "", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/channels/ChannelsKt")
/* compiled from: Deprecated.kt */
final /* synthetic */ class ChannelsKt__DeprecatedKt {
    public static final Function1<Throwable, Unit> consumesAll(ReceiveChannel<?>... channels) {
        return new ChannelsKt__DeprecatedKt$consumesAll$1(channels);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r15.L$0 = r7;
        r15.L$1 = r6;
        r15.I$0 = r5;
        r15.I$1 = r2;
        r15.label = 1;
        r9 = r6.hasNext(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0078, code lost:
        if (r9 != r1) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007a, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007b, code lost:
        r11 = r1;
        r1 = r0;
        r0 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r2;
        r2 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008a, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008c, code lost:
        r0 = r7.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0090, code lost:
        r10 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0093, code lost:
        if (r6 != r5) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0095, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0099, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009a, code lost:
        r0 = r8;
        r8 = r9;
        r5 = r6;
        r6 = r7;
        r7 = r0;
        r0 = r1;
        r1 = r2;
        r2 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a3, code lost:
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00bf, code lost:
        throw new java.lang.IndexOutOfBoundsException("ReceiveChannel doesn't contain element at index " + r6 + '.');
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c0, code lost:
        r14 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c1, code lost:
        r5 = r6;
        r11 = r1;
        r1 = r8;
        r0 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c6, code lost:
        r14 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c7, code lost:
        r2 = r9;
        r5 = r6;
        r11 = r1;
        r1 = r8;
        r0 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ce, code lost:
        r14 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00cf, code lost:
        r1 = r7;
        r2 = r8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object elementAt(kotlinx.coroutines.channels.ReceiveChannel r13, int r14, kotlin.coroutines.Continuation r15) {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r15 = r0.label
            int r15 = r15 - r2
            r0.label = r15
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1
            r0.<init>(r15)
        L_0x0019:
            r15 = r0
            java.lang.Object r0 = r15.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r15.label
            r3 = 46
            java.lang.String r4 = "ReceiveChannel doesn't contain element at index "
            switch(r2) {
                case 0: goto L_0x0050;
                case 1: goto L_0x0031;
                default: goto L_0x0029;
            }
        L_0x0029:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x0031:
            r13 = 0
            r14 = 0
            int r2 = r15.I$1
            int r5 = r15.I$0
            java.lang.Object r6 = r15.L$1
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r15.L$0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            r8 = 0
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x004b }
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r2
            r2 = r1
            r1 = r0
            goto L_0x0084
        L_0x004b:
            r14 = move-exception
            r1 = r7
            r2 = r8
            goto L_0x00f3
        L_0x0050:
            kotlin.ResultKt.throwOnFailure(r0)
            r5 = r14
            r14 = 0
            r2 = 0
            r6 = r13
            r7 = 0
            if (r5 < 0) goto L_0x00d8
            r8 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r6.iterator()     // Catch:{ all -> 0x00d2 }
            r6 = r9
            r11 = r7
            r7 = r13
            r13 = r14
            r14 = r11
            r12 = r8
            r8 = r2
            r2 = r12
        L_0x0069:
            r15.L$0 = r7     // Catch:{ all -> 0x00ce }
            r15.L$1 = r6     // Catch:{ all -> 0x00ce }
            r15.I$0 = r5     // Catch:{ all -> 0x00ce }
            r15.I$1 = r2     // Catch:{ all -> 0x00ce }
            r9 = 1
            r15.label = r9     // Catch:{ all -> 0x00ce }
            java.lang.Object r9 = r6.hasNext(r15)     // Catch:{ all -> 0x00ce }
            if (r9 != r1) goto L_0x007b
            return r1
        L_0x007b:
            r11 = r1
            r1 = r0
            r0 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r2
            r2 = r11
        L_0x0084:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00c6 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00c6 }
            if (r0 == 0) goto L_0x00a3
            java.lang.Object r0 = r7.next()     // Catch:{ all -> 0x00c6 }
            int r10 = r5 + 1
            if (r6 != r5) goto L_0x009a
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r9)
            return r0
        L_0x009a:
            r0 = r8
            r8 = r9
            r5 = r6
            r6 = r7
            r7 = r0
            r0 = r1
            r1 = r2
            r2 = r10
            goto L_0x0069
        L_0x00a3:
            r0 = r8
            r2 = r9
            java.lang.IndexOutOfBoundsException r7 = new java.lang.IndexOutOfBoundsException     // Catch:{ all -> 0x00c0 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c0 }
            r8.<init>()     // Catch:{ all -> 0x00c0 }
            java.lang.StringBuilder r4 = r8.append(r4)     // Catch:{ all -> 0x00c0 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ all -> 0x00c0 }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x00c0 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00c0 }
            r7.<init>(r3)     // Catch:{ all -> 0x00c0 }
            throw r7     // Catch:{ all -> 0x00c0 }
        L_0x00c0:
            r14 = move-exception
            r5 = r6
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x00f3
        L_0x00c6:
            r14 = move-exception
            r0 = r8
            r2 = r9
            r5 = r6
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x00f3
        L_0x00ce:
            r14 = move-exception
            r1 = r7
            r2 = r8
            goto L_0x00f3
        L_0x00d2:
            r1 = move-exception
            r11 = r1
            r1 = r13
            r13 = r14
            r14 = r11
            goto L_0x00f3
        L_0x00d8:
            java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException     // Catch:{ all -> 0x00d2 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d2 }
            r6.<init>()     // Catch:{ all -> 0x00d2 }
            java.lang.StringBuilder r4 = r6.append(r4)     // Catch:{ all -> 0x00d2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00d2 }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x00d2 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00d2 }
            r1.<init>(r3)     // Catch:{ all -> 0x00d2 }
            throw r1     // Catch:{ all -> 0x00d2 }
        L_0x00f3:
            r2 = r14
            throw r14     // Catch:{ all -> 0x00f6 }
        L_0x00f6:
            r14 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r2)
            goto L_0x00fc
        L_0x00fb:
            throw r14
        L_0x00fc:
            goto L_0x00fb
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.elementAt(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r13.L$0 = r7;
        r13.L$1 = r5;
        r13.I$0 = r4;
        r13.I$1 = r2;
        r13.label = 1;
        r8 = r5.hasNext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0077, code lost:
        if (r8 != r1) goto L_0x007a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0079, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007a, code lost:
        r10 = r1;
        r1 = r0;
        r0 = r8;
        r8 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r2;
        r2 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0088, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008a, code lost:
        r0 = r6.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008e, code lost:
        r9 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0091, code lost:
        if (r5 != r4) goto L_0x0098;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0093, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0097, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0098, code lost:
        r4 = r5;
        r5 = r6;
        r6 = r8;
        r0 = r1;
        r1 = r2;
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a0, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a4, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a5, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a6, code lost:
        r2 = r8;
        r4 = r5;
        r10 = r1;
        r1 = r7;
        r0 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ad, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ae, code lost:
        r2 = r6;
        r1 = r7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object elementAtOrNull(kotlinx.coroutines.channels.ReceiveChannel r11, int r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1
            r0.<init>(r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x004c;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002e:
            r11 = 0
            r12 = 0
            int r2 = r13.I$1
            int r4 = r13.I$0
            java.lang.Object r5 = r13.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r13.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0047 }
            r8 = r3
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r2
            r2 = r1
            r1 = r0
            goto L_0x0082
        L_0x0047:
            r11 = move-exception
            r1 = r6
            r2 = r3
            goto L_0x00b6
        L_0x004c:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r12
            r12 = 0
            r2 = r11
            r5 = 0
            if (r4 >= 0) goto L_0x005e
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r3)
            return r3
        L_0x005e:
            r6 = r3
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r2.iterator()     // Catch:{ all -> 0x00b1 }
            r2 = r7
            r7 = r11
            r11 = r5
            r5 = r8
        L_0x0068:
            r13.L$0 = r7     // Catch:{ all -> 0x00ad }
            r13.L$1 = r5     // Catch:{ all -> 0x00ad }
            r13.I$0 = r4     // Catch:{ all -> 0x00ad }
            r13.I$1 = r2     // Catch:{ all -> 0x00ad }
            r8 = 1
            r13.label = r8     // Catch:{ all -> 0x00ad }
            java.lang.Object r8 = r5.hasNext(r13)     // Catch:{ all -> 0x00ad }
            if (r8 != r1) goto L_0x007a
            return r1
        L_0x007a:
            r10 = r1
            r1 = r0
            r0 = r8
            r8 = r6
            r6 = r5
            r5 = r4
            r4 = r2
            r2 = r10
        L_0x0082:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00a5 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00a5 }
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = r6.next()     // Catch:{ all -> 0x00a5 }
            int r9 = r4 + 1
            if (r5 != r4) goto L_0x0098
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r8)
            return r0
        L_0x0098:
            r0 = r8
            r4 = r5
            r5 = r6
            r6 = r0
            r0 = r1
            r1 = r2
            r2 = r9
            goto L_0x0068
        L_0x00a0:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r8)
            return r3
        L_0x00a5:
            r11 = move-exception
            r0 = r7
            r2 = r8
            r4 = r5
            r10 = r1
            r1 = r0
            r0 = r10
            goto L_0x00b6
        L_0x00ad:
            r11 = move-exception
            r2 = r6
            r1 = r7
            goto L_0x00b6
        L_0x00b1:
            r1 = move-exception
            r2 = r6
            r10 = r1
            r1 = r11
            r11 = r10
        L_0x00b6:
            r2 = r11
            throw r11     // Catch:{ all -> 0x00b9 }
        L_0x00b9:
            r11 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r2)
            goto L_0x00bf
        L_0x00be:
            throw r11
        L_0x00bf:
            goto L_0x00be
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.elementAtOrNull(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0065, code lost:
        if (((java.lang.Boolean) r6).booleanValue() == false) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0067, code lost:
        r5 = r2.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006b, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006e, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0076, code lost:
        throw new java.util.NoSuchElementException("ReceiveChannel is empty.");
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object first(kotlinx.coroutines.channels.ReceiveChannel r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1
            r0.<init>(r8)
        L_0x0019:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0041;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002d:
            r7 = 0
            r1 = 0
            java.lang.Object r2 = r8.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r8.L$0
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            r4 = 0
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x003d }
            r6 = r0
            goto L_0x005f
        L_0x003d:
            r1 = move-exception
            r2 = r3
            r3 = r4
            goto L_0x007a
        L_0x0041:
            kotlin.ResultKt.throwOnFailure(r0)
            r3 = r7
            r7 = 0
            r4 = 0
            r2 = r3
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r6 = r2.iterator()     // Catch:{ all -> 0x0077 }
            r2 = r6
            r8.L$0 = r3     // Catch:{ all -> 0x0077 }
            r8.L$1 = r2     // Catch:{ all -> 0x0077 }
            r6 = 1
            r8.label = r6     // Catch:{ all -> 0x0077 }
            java.lang.Object r6 = r2.hasNext(r8)     // Catch:{ all -> 0x0077 }
            if (r6 != r1) goto L_0x005e
            return r1
        L_0x005e:
            r1 = r5
        L_0x005f:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x003d }
            boolean r5 = r6.booleanValue()     // Catch:{ all -> 0x003d }
            if (r5 == 0) goto L_0x006f
            java.lang.Object r5 = r2.next()     // Catch:{ all -> 0x003d }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4)
            return r5
        L_0x006f:
            java.util.NoSuchElementException r5 = new java.util.NoSuchElementException     // Catch:{ all -> 0x003d }
            java.lang.String r6 = "ReceiveChannel is empty."
            r5.<init>(r6)     // Catch:{ all -> 0x003d }
            throw r5     // Catch:{ all -> 0x003d }
        L_0x0077:
            r1 = move-exception
            r2 = r3
            r3 = r4
        L_0x007a:
            r3 = r1
            throw r1     // Catch:{ all -> 0x007d }
        L_0x007d:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.first(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0066, code lost:
        if (((java.lang.Boolean) r7).booleanValue() != false) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0069, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006c, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r3 = r5.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0072, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0073, code lost:
        r3 = r2;
        r2 = r4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object firstOrNull(kotlinx.coroutines.channels.ReceiveChannel r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1
            r0.<init>(r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0042;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002e:
            r9 = 0
            r1 = 0
            java.lang.Object r2 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r4 = r10.L$0
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x003f }
            r7 = r0
            r5 = r2
            r2 = r3
            goto L_0x0060
        L_0x003f:
            r1 = move-exception
            r2 = r4
            goto L_0x007b
        L_0x0042:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r9
            r9 = 0
            r2 = 0
            r5 = r4
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch:{ all -> 0x0078 }
            r5 = r7
            r10.L$0 = r4     // Catch:{ all -> 0x0078 }
            r10.L$1 = r5     // Catch:{ all -> 0x0078 }
            r7 = 1
            r10.label = r7     // Catch:{ all -> 0x0078 }
            java.lang.Object r7 = r5.hasNext(r10)     // Catch:{ all -> 0x0078 }
            if (r7 != r1) goto L_0x005f
            return r1
        L_0x005f:
            r1 = r6
        L_0x0060:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x0072 }
            boolean r6 = r7.booleanValue()     // Catch:{ all -> 0x0072 }
            if (r6 != 0) goto L_0x006d
        L_0x0069:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r2)
            return r3
        L_0x006d:
            java.lang.Object r3 = r5.next()     // Catch:{ all -> 0x0072 }
            goto L_0x0069
        L_0x0072:
            r1 = move-exception
            r3 = r4
            r8 = r3
            r3 = r2
            r2 = r8
            goto L_0x007b
        L_0x0078:
            r1 = move-exception
            r3 = r2
            r2 = r4
        L_0x007b:
            r3 = r1
            throw r1     // Catch:{ all -> 0x007e }
        L_0x007e:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            goto L_0x0084
        L_0x0083:
            throw r1
        L_0x0084:
            goto L_0x0083
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.firstOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r1.L$0 = r10;
        r1.L$1 = r9;
        r1.L$2 = r8;
        r1.L$3 = r7;
        r1.label = 1;
        r12 = r7.hasNext(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0082, code lost:
        if (r12 != r0) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0084, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0085, code lost:
        r16 = r3;
        r3 = r2;
        r2 = r12;
        r12 = r11;
        r11 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0098, code lost:
        if (((java.lang.Boolean) r2).booleanValue() == false) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a4, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r11, r8.next()) == false) goto L_0x00bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r10.element);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ac, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00af, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b0, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b1, code lost:
        r4 = r9;
        r9 = r10;
        r10 = r11;
        r16 = r6;
        r6 = r12;
        r2 = r3;
        r3 = r5;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bf, code lost:
        r2 = r9;
        r9 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r10.element++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c6, code lost:
        r16 = r8;
        r8 = r2;
        r2 = r3;
        r3 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r16;
        r17 = r11;
        r11 = r9;
        r9 = r10;
        r10 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d7, code lost:
        r4 = r2;
        r2 = r3;
        r3 = r5;
        r5 = r6;
        r6 = r9;
        r9 = r10;
        r10 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00df, code lost:
        r4 = r9;
        r2 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e4, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ee, code lost:
        return kotlin.coroutines.jvm.internal.Boxing.boxInt(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ef, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00f0, code lost:
        r9 = r10;
        r10 = r11;
        r16 = r6;
        r6 = r2;
        r2 = r3;
        r3 = r5;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00fa, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00fb, code lost:
        r4 = r9;
        r9 = r10;
        r10 = r11;
        r16 = r6;
        r6 = r12;
        r2 = r3;
        r3 = r5;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0107, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0108, code lost:
        r4 = r8;
        r6 = r11;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object indexOf(kotlinx.coroutines.channels.ReceiveChannel r18, java.lang.Object r19, kotlin.coroutines.Continuation r20) {
        /*
            r0 = r20
            boolean r1 = r0 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1
            if (r1 == 0) goto L_0x0016
            r1 = r0
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1 r1 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1 r1 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1
            r1.<init>(r0)
        L_0x001b:
            r0 = r1
            java.lang.Object r2 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r1.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x0054;
                case 1: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0030:
            r3 = 0
            r5 = 0
            r6 = 0
            java.lang.Object r7 = r1.L$3
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r8 = r1.L$2
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r9 = r1.L$1
            kotlin.jvm.internal.Ref$IntRef r9 = (kotlin.jvm.internal.Ref.IntRef) r9
            java.lang.Object r10 = r1.L$0
            r11 = 0
            kotlin.ResultKt.throwOnFailure(r2)     // Catch:{ all -> 0x004f }
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r2
            goto L_0x0092
        L_0x004f:
            r0 = move-exception
            r4 = r8
            r6 = r11
            goto L_0x0110
        L_0x0054:
            kotlin.ResultKt.throwOnFailure(r2)
            r3 = r18
            r10 = r19
            kotlin.jvm.internal.Ref$IntRef r5 = new kotlin.jvm.internal.Ref$IntRef
            r5.<init>()
            r9 = r5
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = r3
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r8.iterator()     // Catch:{ all -> 0x010b }
            r8 = r3
            r3 = r5
            r5 = r6
            r6 = r11
            r11 = r7
            r7 = r12
        L_0x0074:
            r1.L$0 = r10     // Catch:{ all -> 0x0107 }
            r1.L$1 = r9     // Catch:{ all -> 0x0107 }
            r1.L$2 = r8     // Catch:{ all -> 0x0107 }
            r1.L$3 = r7     // Catch:{ all -> 0x0107 }
            r1.label = r4     // Catch:{ all -> 0x0107 }
            java.lang.Object r12 = r7.hasNext(r1)     // Catch:{ all -> 0x0107 }
            if (r12 != r0) goto L_0x0085
            return r0
        L_0x0085:
            r16 = r3
            r3 = r2
            r2 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r16
        L_0x0092:
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ all -> 0x00fa }
            boolean r2 = r2.booleanValue()     // Catch:{ all -> 0x00fa }
            if (r2 == 0) goto L_0x00df
            java.lang.Object r2 = r8.next()     // Catch:{ all -> 0x00fa }
            r13 = r2
            r14 = 0
            boolean r15 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r11, (java.lang.Object) r13)     // Catch:{ all -> 0x00fa }
            if (r15 == 0) goto L_0x00bf
            int r0 = r10.element     // Catch:{ all -> 0x00b0 }
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)     // Catch:{ all -> 0x00b0 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r12)
            return r0
        L_0x00b0:
            r0 = move-exception
            r4 = r9
            r2 = r12
            r7 = r11
            r9 = r10
            r10 = r7
            r16 = r6
            r6 = r2
            r2 = r3
            r3 = r5
            r5 = r16
            goto L_0x0110
        L_0x00bf:
            r2 = r9
            r9 = r12
            int r12 = r10.element     // Catch:{ all -> 0x00d6 }
            int r12 = r12 + r4
            r10.element = r12     // Catch:{ all -> 0x00d6 }
            r16 = r8
            r8 = r2
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r7 = r16
            r17 = r11
            r11 = r9
            r9 = r10
            r10 = r17
            goto L_0x0074
        L_0x00d6:
            r0 = move-exception
            r4 = r2
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r9
            r9 = r10
            r10 = r11
            goto L_0x0110
        L_0x00df:
            r4 = r9
            r2 = r12
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ef }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r2)
            r0 = -1
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x00ef:
            r0 = move-exception
            r9 = r10
            r10 = r11
            r16 = r6
            r6 = r2
            r2 = r3
            r3 = r5
            r5 = r16
            goto L_0x0110
        L_0x00fa:
            r0 = move-exception
            r4 = r9
            r2 = r12
            r9 = r10
            r10 = r11
            r16 = r6
            r6 = r2
            r2 = r3
            r3 = r5
            r5 = r16
            goto L_0x0110
        L_0x0107:
            r0 = move-exception
            r4 = r8
            r6 = r11
            goto L_0x0110
        L_0x010b:
            r0 = move-exception
            r4 = r3
            r3 = r5
            r5 = r6
            r6 = r7
        L_0x0110:
            r6 = r0
            throw r0     // Catch:{ all -> 0x0113 }
        L_0x0113:
            r0 = move-exception
            r7 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r6)
            goto L_0x011a
        L_0x0119:
            throw r7
        L_0x011a:
            goto L_0x0119
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.indexOf(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0089, code lost:
        if (((java.lang.Boolean) r6).booleanValue() == false) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008f, code lost:
        r8 = r5;
        r5 = r2;
        r2 = r8;
        r9 = r4;
        r4 = r3;
        r3 = r3.next();
        r6 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r11.L$0 = r5;
        r11.L$1 = r4;
        r11.L$2 = r3;
        r11.label = 2;
        r7 = r4.hasNext(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a3, code lost:
        if (r7 != r1) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a5, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a6, code lost:
        r8 = r1;
        r1 = r0;
        r0 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b5, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00bb, code lost:
        r4 = r6;
        r6 = r7;
        r8 = r3;
        r3 = r5.next();
        r0 = r1;
        r1 = r2;
        r2 = r8;
        r9 = r5;
        r5 = r4;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c6, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ca, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00cb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00cc, code lost:
        r2 = r6;
        r3 = r7;
        r8 = r1;
        r1 = r0;
        r0 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00d2, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d3, code lost:
        r2 = r5;
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00dd, code lost:
        throw new java.util.NoSuchElementException("ReceiveChannel is empty.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00de, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00df, code lost:
        r3 = r4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object last(kotlinx.coroutines.channels.ReceiveChannel r10, kotlin.coroutines.Continuation r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1
            r0.<init>(r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            switch(r2) {
                case 0: goto L_0x0063;
                case 1: goto L_0x004b;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002d:
            r10 = 0
            r2 = 0
            java.lang.Object r3 = r11.L$2
            java.lang.Object r4 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r11.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            r6 = 0
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0046 }
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x00af
        L_0x0046:
            r1 = move-exception
            r2 = r5
            r3 = r6
            goto L_0x00e2
        L_0x004b:
            r10 = 0
            r2 = 0
            java.lang.Object r3 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            r4 = 0
            java.lang.Object r5 = r11.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x005e }
            r6 = r0
            r8 = r5
            r5 = r2
            r2 = r8
            goto L_0x0083
        L_0x005e:
            r1 = move-exception
            r3 = r4
            r2 = r5
            goto L_0x00e2
        L_0x0063:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r10
            r10 = 0
            r3 = 0
            r4 = r2
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r6 = r4.iterator()     // Catch:{ all -> 0x00e1 }
            r4 = r6
            r11.L$0 = r2     // Catch:{ all -> 0x00e1 }
            r11.L$1 = r4     // Catch:{ all -> 0x00e1 }
            r6 = 1
            r11.label = r6     // Catch:{ all -> 0x00e1 }
            java.lang.Object r6 = r4.hasNext(r11)     // Catch:{ all -> 0x00e1 }
            if (r6 != r1) goto L_0x0080
            return r1
        L_0x0080:
            r8 = r4
            r4 = r3
            r3 = r8
        L_0x0083:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x00de }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x00de }
            if (r6 == 0) goto L_0x00d6
            java.lang.Object r6 = r3.next()     // Catch:{ all -> 0x00de }
            r8 = r5
            r5 = r2
            r2 = r8
            r9 = r4
            r4 = r3
            r3 = r6
            r6 = r9
        L_0x0096:
            r11.L$0 = r5     // Catch:{ all -> 0x00d2 }
            r11.L$1 = r4     // Catch:{ all -> 0x00d2 }
            r11.L$2 = r3     // Catch:{ all -> 0x00d2 }
            r7 = 2
            r11.label = r7     // Catch:{ all -> 0x00d2 }
            java.lang.Object r7 = r4.hasNext(r11)     // Catch:{ all -> 0x00d2 }
            if (r7 != r1) goto L_0x00a6
            return r1
        L_0x00a6:
            r8 = r1
            r1 = r0
            r0 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r8
        L_0x00af:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00cb }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00cb }
            if (r0 == 0) goto L_0x00c6
            java.lang.Object r0 = r5.next()     // Catch:{ all -> 0x00cb }
            r4 = r6
            r6 = r7
            r8 = r3
            r3 = r0
            r0 = r1
            r1 = r2
            r2 = r8
            r9 = r5
            r5 = r4
            r4 = r9
            goto L_0x0096
        L_0x00c6:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r7)
            return r4
        L_0x00cb:
            r0 = move-exception
            r2 = r6
            r3 = r7
            r8 = r1
            r1 = r0
            r0 = r8
            goto L_0x00e2
        L_0x00d2:
            r1 = move-exception
            r2 = r5
            r3 = r6
            goto L_0x00e2
        L_0x00d6:
            java.util.NoSuchElementException r1 = new java.util.NoSuchElementException     // Catch:{ all -> 0x00de }
            java.lang.String r6 = "ReceiveChannel is empty."
            r1.<init>(r6)     // Catch:{ all -> 0x00de }
            throw r1     // Catch:{ all -> 0x00de }
        L_0x00de:
            r1 = move-exception
            r3 = r4
            goto L_0x00e2
        L_0x00e1:
            r1 = move-exception
        L_0x00e2:
            r3 = r1
            throw r1     // Catch:{ all -> 0x00e5 }
        L_0x00e5:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            goto L_0x00eb
        L_0x00ea:
            throw r1
        L_0x00eb:
            goto L_0x00ea
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.last(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r1.L$0 = r12;
        r1.L$1 = r11;
        r1.L$2 = r10;
        r1.L$3 = r9;
        r1.L$4 = r7;
        r1.label = r4;
        r13 = r7.hasNext(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0091, code lost:
        if (r13 != r0) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0093, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0094, code lost:
        r17 = r3;
        r3 = r2;
        r2 = r13;
        r13 = r12;
        r12 = r11;
        r11 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a8, code lost:
        if (((java.lang.Boolean) r2).booleanValue() == false) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b4, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r13, r8.next()) == false) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b6, code lost:
        r12.element = r11.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ba, code lost:
        r11.element++;
        r2 = r3;
        r3 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r9 = r10;
        r10 = r11;
        r11 = r12;
        r12 = r13;
        r4 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ce, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00d1, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00dc, code lost:
        return kotlin.coroutines.jvm.internal.Boxing.boxInt(r12.element);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00dd, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00de, code lost:
        r2 = r3;
        r3 = r5;
        r5 = r6;
        r8 = r9;
        r9 = r10;
        r10 = r11;
        r11 = r12;
        r12 = r13;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object lastIndexOf(kotlinx.coroutines.channels.ReceiveChannel r18, java.lang.Object r19, kotlin.coroutines.Continuation r20) {
        /*
            r0 = r20
            boolean r1 = r0 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1
            if (r1 == 0) goto L_0x0016
            r1 = r0
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1 r1 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1 r1 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1
            r1.<init>(r0)
        L_0x001b:
            r0 = r1
            java.lang.Object r2 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r1.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x0057;
                case 1: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0030:
            r3 = 0
            r5 = 0
            r6 = 0
            java.lang.Object r7 = r1.L$4
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            r8 = 0
            java.lang.Object r9 = r1.L$3
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r10 = r1.L$2
            kotlin.jvm.internal.Ref$IntRef r10 = (kotlin.jvm.internal.Ref.IntRef) r10
            java.lang.Object r11 = r1.L$1
            kotlin.jvm.internal.Ref$IntRef r11 = (kotlin.jvm.internal.Ref.IntRef) r11
            java.lang.Object r12 = r1.L$0
            kotlin.ResultKt.throwOnFailure(r2)     // Catch:{ all -> 0x0054 }
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r2
            goto L_0x00a2
        L_0x0054:
            r0 = move-exception
            goto L_0x00ed
        L_0x0057:
            kotlin.ResultKt.throwOnFailure(r2)
            r3 = r18
            r12 = r19
            kotlin.jvm.internal.Ref$IntRef r5 = new kotlin.jvm.internal.Ref$IntRef
            r5.<init>()
            r11 = r5
            r5 = -1
            r11.element = r5
            kotlin.jvm.internal.Ref$IntRef r5 = new kotlin.jvm.internal.Ref$IntRef
            r5.<init>()
            r10 = r5
            r5 = 0
            r9 = r3
            r3 = 0
            r8 = 0
            r6 = r9
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r13 = r6.iterator()     // Catch:{ all -> 0x00e7 }
            r6 = r7
            r7 = r13
            r17 = r5
            r5 = r3
            r3 = r17
        L_0x0081:
            r1.L$0 = r12     // Catch:{ all -> 0x0054 }
            r1.L$1 = r11     // Catch:{ all -> 0x0054 }
            r1.L$2 = r10     // Catch:{ all -> 0x0054 }
            r1.L$3 = r9     // Catch:{ all -> 0x0054 }
            r1.L$4 = r7     // Catch:{ all -> 0x0054 }
            r1.label = r4     // Catch:{ all -> 0x0054 }
            java.lang.Object r13 = r7.hasNext(r1)     // Catch:{ all -> 0x0054 }
            if (r13 != r0) goto L_0x0094
            return r0
        L_0x0094:
            r17 = r3
            r3 = r2
            r2 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r17
        L_0x00a2:
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ all -> 0x00dd }
            boolean r2 = r2.booleanValue()     // Catch:{ all -> 0x00dd }
            if (r2 == 0) goto L_0x00ce
            java.lang.Object r2 = r8.next()     // Catch:{ all -> 0x00dd }
            r14 = r2
            r15 = 0
            boolean r16 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r13, (java.lang.Object) r14)     // Catch:{ all -> 0x00dd }
            if (r16 == 0) goto L_0x00ba
            int r4 = r11.element     // Catch:{ all -> 0x00dd }
            r12.element = r4     // Catch:{ all -> 0x00dd }
        L_0x00ba:
            int r4 = r11.element     // Catch:{ all -> 0x00dd }
            r16 = 1
            int r4 = r4 + 1
            r11.element = r4     // Catch:{ all -> 0x00dd }
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r4 = 1
            goto L_0x0081
        L_0x00ce:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00dd }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r9)
            int r0 = r12.element
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x00dd:
            r0 = move-exception
            r2 = r3
            r3 = r5
            r5 = r6
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            goto L_0x00ed
        L_0x00e7:
            r0 = move-exception
            r17 = r5
            r5 = r3
            r3 = r17
        L_0x00ed:
            r4 = r0
            throw r0     // Catch:{ all -> 0x00f0 }
        L_0x00f0:
            r0 = move-exception
            r6 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            goto L_0x00f7
        L_0x00f6:
            throw r6
        L_0x00f7:
            goto L_0x00f6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.lastIndexOf(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0085, code lost:
        if (((java.lang.Boolean) r7).booleanValue() != false) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0087, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008b, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008c, code lost:
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0091, code lost:
        r6 = r3;
        r3 = r4;
        r4 = r5.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r11.L$0 = r6;
        r11.L$1 = r5;
        r11.L$2 = r4;
        r11.label = 2;
        r7 = r5.hasNext(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a2, code lost:
        if (r7 != r1) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a4, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a5, code lost:
        r8 = r1;
        r1 = r0;
        r0 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b4, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ba, code lost:
        r8 = r4;
        r4 = r6.next();
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r8;
        r9 = r6;
        r6 = r7;
        r5 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c5, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c9, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00ca, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00cb, code lost:
        r8 = r1;
        r1 = r7;
        r0 = r8;
        r9 = r3;
        r3 = r4;
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d4, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d5, code lost:
        r1 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00d7, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d8, code lost:
        r1 = r3;
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00db, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00dc, code lost:
        r1 = r6;
        r3 = r4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object lastOrNull(kotlinx.coroutines.channels.ReceiveChannel r10, kotlin.coroutines.Continuation r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1
            r0.<init>(r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x005f;
                case 1: goto L_0x0049;
                case 2: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002e:
            r10 = 0
            r2 = 0
            java.lang.Object r4 = r11.L$2
            java.lang.Object r5 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r11.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0046 }
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x00ae
        L_0x0046:
            r10 = move-exception
            r1 = r6
            goto L_0x005d
        L_0x0049:
            r2 = 0
            r10 = 0
            java.lang.Object r4 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r11.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x005b }
            r7 = r0
            r6 = r5
            r5 = r4
            r4 = r3
            goto L_0x007f
        L_0x005b:
            r10 = move-exception
            r1 = r5
        L_0x005d:
            goto L_0x00e5
        L_0x005f:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = 0
            r4 = 0
            r5 = r10
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch:{ all -> 0x00e0 }
            r5 = r7
            r11.L$0 = r10     // Catch:{ all -> 0x00e0 }
            r11.L$1 = r5     // Catch:{ all -> 0x00e0 }
            r7 = 1
            r11.label = r7     // Catch:{ all -> 0x00e0 }
            java.lang.Object r7 = r5.hasNext(r11)     // Catch:{ all -> 0x00e0 }
            if (r7 != r1) goto L_0x007c
            return r1
        L_0x007c:
            r8 = r6
            r6 = r10
            r10 = r8
        L_0x007f:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x00db }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x00db }
            if (r7 != 0) goto L_0x008c
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4)
            return r3
        L_0x008c:
            r3 = r6
            java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x00d7 }
            r8 = r6
            r6 = r3
            r3 = r4
            r4 = r8
        L_0x0095:
            r11.L$0 = r6     // Catch:{ all -> 0x00d4 }
            r11.L$1 = r5     // Catch:{ all -> 0x00d4 }
            r11.L$2 = r4     // Catch:{ all -> 0x00d4 }
            r7 = 2
            r11.label = r7     // Catch:{ all -> 0x00d4 }
            java.lang.Object r7 = r5.hasNext(r11)     // Catch:{ all -> 0x00d4 }
            if (r7 != r1) goto L_0x00a5
            return r1
        L_0x00a5:
            r8 = r1
            r1 = r0
            r0 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r8
        L_0x00ae:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00ca }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00ca }
            if (r0 == 0) goto L_0x00c5
            java.lang.Object r0 = r6.next()     // Catch:{ all -> 0x00ca }
            r5 = r7
            r8 = r4
            r4 = r0
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r8
            r9 = r6
            r6 = r5
            r5 = r9
            goto L_0x0095
        L_0x00c5:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r4)
            return r5
        L_0x00ca:
            r10 = move-exception
            r0 = r7
            r2 = r4
            r8 = r1
            r1 = r0
            r0 = r8
            r9 = r3
            r3 = r2
            r2 = r9
            goto L_0x00e5
        L_0x00d4:
            r10 = move-exception
            r1 = r6
            goto L_0x00e5
        L_0x00d7:
            r10 = move-exception
            r1 = r3
            r3 = r4
            goto L_0x00e5
        L_0x00db:
            r10 = move-exception
            r1 = r6
            r3 = r4
            goto L_0x005d
        L_0x00e0:
            r1 = move-exception
            r3 = r4
            r8 = r1
            r1 = r10
            r10 = r8
        L_0x00e5:
            r3 = r10
            throw r10     // Catch:{ all -> 0x00e8 }
        L_0x00e8:
            r10 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r3)
            goto L_0x00ee
        L_0x00ed:
            throw r10
        L_0x00ee:
            goto L_0x00ed
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.lastOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007e, code lost:
        if (((java.lang.Boolean) r6).booleanValue() == false) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0080, code lost:
        r6 = r3.next();
        r11.L$0 = r5;
        r11.L$1 = r6;
        r11.label = 2;
        r7 = r3.hasNext(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008f, code lost:
        if (r7 != r1) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0091, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0092, code lost:
        r1 = r2;
        r3 = r5;
        r2 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009b, code lost:
        if (((java.lang.Boolean) r7).booleanValue() != false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009d, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a1, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a9, code lost:
        throw new java.lang.IllegalArgumentException("ReceiveChannel has more than one element.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b1, code lost:
        throw new java.util.NoSuchElementException("ReceiveChannel is empty.");
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object single(kotlinx.coroutines.channels.ReceiveChannel r10, kotlin.coroutines.Continuation r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1
            r0.<init>(r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            switch(r2) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0041;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002d:
            r10 = 0
            r1 = 0
            java.lang.Object r2 = r11.L$1
            java.lang.Object r3 = r11.L$0
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            r4 = 0
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x003c }
            r7 = r0
            goto L_0x0095
        L_0x003c:
            r1 = move-exception
            r2 = r3
            r3 = r4
            goto L_0x00b3
        L_0x0041:
            r10 = 0
            r2 = 0
            java.lang.Object r3 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            r4 = 0
            java.lang.Object r5 = r11.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0051 }
            r6 = r0
            goto L_0x0078
        L_0x0051:
            r1 = move-exception
            r3 = r4
            r2 = r5
            goto L_0x00b3
        L_0x0055:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r10
            r10 = 0
            r3 = 0
            r4 = r2
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r6 = r4.iterator()     // Catch:{ all -> 0x00b2 }
            r4 = r6
            r11.L$0 = r2     // Catch:{ all -> 0x00b2 }
            r11.L$1 = r4     // Catch:{ all -> 0x00b2 }
            r6 = 1
            r11.label = r6     // Catch:{ all -> 0x00b2 }
            java.lang.Object r6 = r4.hasNext(r11)     // Catch:{ all -> 0x00b2 }
            if (r6 != r1) goto L_0x0072
            return r1
        L_0x0072:
            r8 = r5
            r5 = r2
            r2 = r8
            r9 = r4
            r4 = r3
            r3 = r9
        L_0x0078:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x0051 }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x0051 }
            if (r6 == 0) goto L_0x00aa
            java.lang.Object r6 = r3.next()     // Catch:{ all -> 0x0051 }
            r11.L$0 = r5     // Catch:{ all -> 0x0051 }
            r11.L$1 = r6     // Catch:{ all -> 0x0051 }
            r7 = 2
            r11.label = r7     // Catch:{ all -> 0x0051 }
            java.lang.Object r7 = r3.hasNext(r11)     // Catch:{ all -> 0x0051 }
            if (r7 != r1) goto L_0x0092
            return r1
        L_0x0092:
            r1 = r2
            r3 = r5
            r2 = r6
        L_0x0095:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x003c }
            boolean r5 = r7.booleanValue()     // Catch:{ all -> 0x003c }
            if (r5 != 0) goto L_0x00a2
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4)
            return r2
        L_0x00a2:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x003c }
            java.lang.String r6 = "ReceiveChannel has more than one element."
            r5.<init>(r6)     // Catch:{ all -> 0x003c }
            throw r5     // Catch:{ all -> 0x003c }
        L_0x00aa:
            java.util.NoSuchElementException r1 = new java.util.NoSuchElementException     // Catch:{ all -> 0x0051 }
            java.lang.String r3 = "ReceiveChannel is empty."
            r1.<init>(r3)     // Catch:{ all -> 0x0051 }
            throw r1     // Catch:{ all -> 0x0051 }
        L_0x00b2:
            r1 = move-exception
        L_0x00b3:
            r3 = r1
            throw r1     // Catch:{ all -> 0x00b6 }
        L_0x00b6:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.single(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0084, code lost:
        if (((java.lang.Boolean) r7).booleanValue() != false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0086, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008a, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r7 = r5.next();
        r11.L$0 = r6;
        r11.L$1 = r7;
        r11.label = 2;
        r8 = r5.hasNext(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009a, code lost:
        if (r8 != r1) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009c, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009d, code lost:
        r1 = r2;
        r5 = r4;
        r4 = r6;
        r2 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a7, code lost:
        if (((java.lang.Boolean) r8).booleanValue() == false) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a9, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ad, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ae, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b2, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b3, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b4, code lost:
        r2 = r4;
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b7, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b8, code lost:
        r1 = r2;
        r3 = r4;
        r2 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00bc, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00bd, code lost:
        r3 = r4;
        r9 = r2;
        r2 = r6;
        r1 = r9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object singleOrNull(kotlinx.coroutines.channels.ReceiveChannel r10, kotlin.coroutines.Continuation r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1
            r0.<init>(r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x005d;
                case 1: goto L_0x0041;
                case 2: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002e:
            r10 = 0
            r1 = 0
            java.lang.Object r2 = r11.L$1
            java.lang.Object r4 = r11.L$0
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x003d }
            r8 = r0
            r5 = r3
            goto L_0x00a1
        L_0x003d:
            r10 = move-exception
            r2 = r4
            goto L_0x00c8
        L_0x0041:
            r10 = 0
            r2 = 0
            java.lang.Object r4 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r11.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0056 }
            r7 = r0
            r6 = r5
            r5 = r4
            r4 = r3
            r9 = r2
            r2 = r10
            r10 = r9
            goto L_0x007e
        L_0x0056:
            r1 = move-exception
            r2 = r5
            r9 = r1
            r1 = r10
            r10 = r9
            goto L_0x00c8
        L_0x005d:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r10
            r10 = 0
            r4 = 0
            r5 = r2
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch:{ all -> 0x00c3 }
            r5 = r7
            r11.L$0 = r2     // Catch:{ all -> 0x00c3 }
            r11.L$1 = r5     // Catch:{ all -> 0x00c3 }
            r7 = 1
            r11.label = r7     // Catch:{ all -> 0x00c3 }
            java.lang.Object r7 = r5.hasNext(r11)     // Catch:{ all -> 0x00c3 }
            if (r7 != r1) goto L_0x007a
            return r1
        L_0x007a:
            r9 = r2
            r2 = r10
            r10 = r6
            r6 = r9
        L_0x007e:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x00bc }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x00bc }
            if (r7 != 0) goto L_0x008b
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4)
            return r3
        L_0x008b:
            java.lang.Object r7 = r5.next()     // Catch:{ all -> 0x00b7 }
            r11.L$0 = r6     // Catch:{ all -> 0x00b7 }
            r11.L$1 = r7     // Catch:{ all -> 0x00b7 }
            r8 = 2
            r11.label = r8     // Catch:{ all -> 0x00b7 }
            java.lang.Object r8 = r5.hasNext(r11)     // Catch:{ all -> 0x00b7 }
            if (r8 != r1) goto L_0x009d
            return r1
        L_0x009d:
            r1 = r2
            r5 = r4
            r4 = r6
            r2 = r7
        L_0x00a1:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch:{ all -> 0x00b3 }
            boolean r6 = r8.booleanValue()     // Catch:{ all -> 0x00b3 }
            if (r6 == 0) goto L_0x00ae
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r5)
            return r3
        L_0x00ae:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r5)
            return r2
        L_0x00b3:
            r10 = move-exception
            r2 = r4
            r3 = r5
            goto L_0x00c8
        L_0x00b7:
            r10 = move-exception
            r1 = r2
            r3 = r4
            r2 = r6
            goto L_0x00c8
        L_0x00bc:
            r10 = move-exception
            r1 = r6
            r3 = r4
            r9 = r2
            r2 = r1
            r1 = r9
            goto L_0x00c8
        L_0x00c3:
            r1 = move-exception
            r3 = r4
            r9 = r1
            r1 = r10
            r10 = r9
        L_0x00c8:
            r3 = r10
            throw r10     // Catch:{ all -> 0x00cb }
        L_0x00cb:
            r10 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.singleOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ ReceiveChannel drop$default(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$drop$1(i, receiveChannel, (Continuation<? super ChannelsKt__DeprecatedKt$drop$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel dropWhile$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$dropWhile$1(receiveChannel, function2, (Continuation<? super ChannelsKt__DeprecatedKt$dropWhile$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel filter$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filter(receiveChannel, coroutineContext, function2);
    }

    public static final <E> ReceiveChannel<E> filter(ReceiveChannel<? extends E> $this$filter, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumes($this$filter), new ChannelsKt__DeprecatedKt$filter$1($this$filter, predicate, (Continuation<? super ChannelsKt__DeprecatedKt$filter$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel filterIndexed$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$filterIndexed$1(receiveChannel, function3, (Continuation<? super ChannelsKt__DeprecatedKt$filterIndexed$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel filterNot$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filter(receiveChannel, coroutineContext, new ChannelsKt__DeprecatedKt$filterNot$1(function2, (Continuation<? super ChannelsKt__DeprecatedKt$filterNot$1>) null));
    }

    public static final <E> ReceiveChannel<E> filterNotNull(ReceiveChannel<? extends E> $this$filterNotNull) {
        return filter$default($this$filterNotNull, (CoroutineContext) null, new ChannelsKt__DeprecatedKt$filterNotNull$1((Continuation<? super ChannelsKt__DeprecatedKt$filterNotNull$1>) null), 1, (Object) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r13.L$0 = r6;
        r13.L$1 = r5;
        r13.L$2 = r3;
        r13.label = 1;
        r7 = r3.hasNext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006e, code lost:
        if (r7 != r1) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0070, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0071, code lost:
        r10 = r1;
        r1 = r0;
        r0 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0080, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0082, code lost:
        r8 = r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0088, code lost:
        if (r8 == null) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008a, code lost:
        r7.add(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008d, code lost:
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0095, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0098, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009d, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009f, code lost:
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r10 = r1;
        r1 = r0;
        r0 = r10;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel r11, java.util.Collection r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1
            r0.<init>(r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = 0
            r12 = 0
            r2 = 0
            java.lang.Object r3 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            r4 = 0
            java.lang.Object r5 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r6 = r13.L$0
            java.util.Collection r6 = (java.util.Collection) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0048 }
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x007a
        L_0x0048:
            r1 = move-exception
            goto L_0x00aa
        L_0x004b:
            kotlin.ResultKt.throwOnFailure(r0)
            r6 = r12
            r12 = 0
            r5 = r11
            r11 = 0
            r4 = 0
            r2 = r5
            r3 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r2.iterator()     // Catch:{ all -> 0x00a6 }
            r2 = r3
            r3 = r7
            r10 = r12
            r12 = r11
            r11 = r10
        L_0x0061:
            r13.L$0 = r6     // Catch:{ all -> 0x0048 }
            r13.L$1 = r5     // Catch:{ all -> 0x0048 }
            r13.L$2 = r3     // Catch:{ all -> 0x0048 }
            r7 = 1
            r13.label = r7     // Catch:{ all -> 0x0048 }
            java.lang.Object r7 = r3.hasNext(r13)     // Catch:{ all -> 0x0048 }
            if (r7 != r1) goto L_0x0071
            return r1
        L_0x0071:
            r10 = r1
            r1 = r0
            r0 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r10
        L_0x007a:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x009e }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x009e }
            if (r0 == 0) goto L_0x0095
            java.lang.Object r0 = r4.next()     // Catch:{ all -> 0x009e }
            r8 = r0
            r9 = 0
            if (r8 == 0) goto L_0x008d
            r7.add(r8)     // Catch:{ all -> 0x009e }
        L_0x008d:
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            goto L_0x0061
        L_0x0095:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x009e }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5)
            return r7
        L_0x009e:
            r0 = move-exception
            r4 = r5
            r5 = r6
            r6 = r7
            r10 = r1
            r1 = r0
            r0 = r10
            goto L_0x00aa
        L_0x00a6:
            r1 = move-exception
            r10 = r12
            r12 = r11
            r11 = r10
        L_0x00aa:
            r2 = r1
            throw r1     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r2)
            goto L_0x00b3
        L_0x00b2:
            throw r1
        L_0x00b3:
            goto L_0x00b2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: kotlinx.coroutines.channels.SendChannel} */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r13.L$0 = r7;
        r13.L$1 = r6;
        r13.L$2 = r4;
        r13.label = 1;
        r2 = r4.hasNext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0081, code lost:
        if (r2 != r1) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0083, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0084, code lost:
        r10 = r1;
        r1 = r0;
        r0 = r2;
        r2 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008e, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0090, code lost:
        r0 = r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0095, code lost:
        if (r0 == null) goto L_0x00ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0097, code lost:
        r13.L$0 = r7;
        r13.L$1 = r6;
        r13.L$2 = r4;
        r13.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a4, code lost:
        if (r7.send(r0, r13) != r2) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a6, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a7, code lost:
        r0 = r1;
        r1 = r2;
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ab, code lost:
        r3 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ad, code lost:
        r0 = r1;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b0, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b3, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b8, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ba, code lost:
        r10 = r1;
        r1 = r0;
        r0 = r10;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel r11, kotlinx.coroutines.channels.SendChannel r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3
            r0.<init>(r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0060;
                case 1: goto L_0x0043;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = 0
            r12 = 0
            r2 = 0
            r3 = 0
            java.lang.Object r4 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r5 = 0
            java.lang.Object r6 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r13.L$0
            kotlinx.coroutines.channels.SendChannel r7 = (kotlinx.coroutines.channels.SendChannel) r7
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x005d }
            goto L_0x00ab
        L_0x0043:
            r11 = 0
            r12 = 0
            r2 = 0
            java.lang.Object r3 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            r5 = 0
            java.lang.Object r4 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            r6 = r4
            java.lang.Object r4 = r13.L$0
            r7 = r4
            kotlinx.coroutines.channels.SendChannel r7 = (kotlinx.coroutines.channels.SendChannel) r7
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x005d }
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x0088
        L_0x005d:
            r1 = move-exception
            goto L_0x00c2
        L_0x0060:
            kotlin.ResultKt.throwOnFailure(r0)
            r7 = r12
            r12 = 0
            r6 = r11
            r11 = 0
            r5 = 0
            r2 = r6
            r3 = 0
            kotlinx.coroutines.channels.ChannelIterator r4 = r2.iterator()     // Catch:{ all -> 0x00be }
            r10 = r12
            r12 = r11
            r11 = r10
        L_0x0074:
            r13.L$0 = r7     // Catch:{ all -> 0x005d }
            r13.L$1 = r6     // Catch:{ all -> 0x005d }
            r13.L$2 = r4     // Catch:{ all -> 0x005d }
            r2 = 1
            r13.label = r2     // Catch:{ all -> 0x005d }
            java.lang.Object r2 = r4.hasNext(r13)     // Catch:{ all -> 0x005d }
            if (r2 != r1) goto L_0x0084
            return r1
        L_0x0084:
            r10 = r1
            r1 = r0
            r0 = r2
            r2 = r10
        L_0x0088:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00b9 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00b9 }
            if (r0 == 0) goto L_0x00b0
            java.lang.Object r0 = r4.next()     // Catch:{ all -> 0x00b9 }
            r8 = 0
            if (r0 == 0) goto L_0x00ad
            r13.L$0 = r7     // Catch:{ all -> 0x00b9 }
            r13.L$1 = r6     // Catch:{ all -> 0x00b9 }
            r13.L$2 = r4     // Catch:{ all -> 0x00b9 }
            r9 = 2
            r13.label = r9     // Catch:{ all -> 0x00b9 }
            java.lang.Object r9 = r7.send(r0, r13)     // Catch:{ all -> 0x00b9 }
            if (r9 != r2) goto L_0x00a7
            return r2
        L_0x00a7:
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r8
        L_0x00ab:
            r3 = r2
            goto L_0x0074
        L_0x00ad:
            r0 = r1
            r1 = r2
            goto L_0x0074
        L_0x00b0:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00b9 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5)
            return r7
        L_0x00b9:
            r0 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
            goto L_0x00c2
        L_0x00be:
            r1 = move-exception
            r10 = r12
            r12 = r11
            r11 = r10
        L_0x00c2:
            r2 = r1
            throw r1     // Catch:{ all -> 0x00c5 }
        L_0x00c5:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r2)
            goto L_0x00cb
        L_0x00ca:
            throw r1
        L_0x00cb:
            goto L_0x00ca
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ ReceiveChannel take$default(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$take$1(i, receiveChannel, (Continuation<? super ChannelsKt__DeprecatedKt$take$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel takeWhile$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$takeWhile$1(receiveChannel, function2, (Continuation<? super ChannelsKt__DeprecatedKt$takeWhile$1>) null), 6, (Object) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r13.L$0 = r7;
        r13.L$1 = r6;
        r13.L$2 = r4;
        r13.label = 1;
        r2 = r4.hasNext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0081, code lost:
        if (r2 != r1) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0083, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0084, code lost:
        r10 = r1;
        r1 = r0;
        r0 = r2;
        r2 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008e, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0090, code lost:
        r0 = r4.next();
        r13.L$0 = r7;
        r13.L$1 = r6;
        r13.L$2 = r4;
        r13.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a2, code lost:
        if (r7.send(r0, r13) != r2) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a4, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a5, code lost:
        r0 = r1;
        r1 = r2;
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a9, code lost:
        r3 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ab, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ae, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b3, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b5, code lost:
        r10 = r1;
        r1 = r0;
        r0 = r10;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object toChannel(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r11, C r12, kotlin.coroutines.Continuation<? super C> r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1
            r0.<init>(r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0060;
                case 1: goto L_0x0043;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = 0
            r12 = 0
            r2 = 0
            r3 = 0
            java.lang.Object r4 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r5 = 0
            java.lang.Object r6 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r13.L$0
            kotlinx.coroutines.channels.SendChannel r7 = (kotlinx.coroutines.channels.SendChannel) r7
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x005d }
            goto L_0x00a9
        L_0x0043:
            r11 = 0
            r12 = 0
            r2 = 0
            java.lang.Object r3 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            r5 = 0
            java.lang.Object r4 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            r6 = r4
            java.lang.Object r4 = r13.L$0
            r7 = r4
            kotlinx.coroutines.channels.SendChannel r7 = (kotlinx.coroutines.channels.SendChannel) r7
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x005d }
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x0088
        L_0x005d:
            r1 = move-exception
            goto L_0x00bd
        L_0x0060:
            kotlin.ResultKt.throwOnFailure(r0)
            r7 = r12
            r12 = 0
            r6 = r11
            r11 = 0
            r5 = 0
            r2 = r6
            r3 = 0
            kotlinx.coroutines.channels.ChannelIterator r4 = r2.iterator()     // Catch:{ all -> 0x00b9 }
            r10 = r12
            r12 = r11
            r11 = r10
        L_0x0074:
            r13.L$0 = r7     // Catch:{ all -> 0x005d }
            r13.L$1 = r6     // Catch:{ all -> 0x005d }
            r13.L$2 = r4     // Catch:{ all -> 0x005d }
            r2 = 1
            r13.label = r2     // Catch:{ all -> 0x005d }
            java.lang.Object r2 = r4.hasNext(r13)     // Catch:{ all -> 0x005d }
            if (r2 != r1) goto L_0x0084
            return r1
        L_0x0084:
            r10 = r1
            r1 = r0
            r0 = r2
            r2 = r10
        L_0x0088:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00b4 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00b4 }
            if (r0 == 0) goto L_0x00ab
            java.lang.Object r0 = r4.next()     // Catch:{ all -> 0x00b4 }
            r8 = 0
            r13.L$0 = r7     // Catch:{ all -> 0x00b4 }
            r13.L$1 = r6     // Catch:{ all -> 0x00b4 }
            r13.L$2 = r4     // Catch:{ all -> 0x00b4 }
            r9 = 2
            r13.label = r9     // Catch:{ all -> 0x00b4 }
            java.lang.Object r9 = r7.send(r0, r13)     // Catch:{ all -> 0x00b4 }
            if (r9 != r2) goto L_0x00a5
            return r2
        L_0x00a5:
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r8
        L_0x00a9:
            r3 = r2
            goto L_0x0074
        L_0x00ab:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00b4 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5)
            return r7
        L_0x00b4:
            r0 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
            goto L_0x00bd
        L_0x00b9:
            r1 = move-exception
            r10 = r12
            r12 = r11
            r11 = r10
        L_0x00bd:
            r2 = r1
            throw r1     // Catch:{ all -> 0x00c0 }
        L_0x00c0:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r2)
            goto L_0x00c6
        L_0x00c5:
            throw r1
        L_0x00c6:
            goto L_0x00c5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toChannel(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r13.L$0 = r6;
        r13.L$1 = r5;
        r13.L$2 = r3;
        r13.label = 1;
        r7 = r3.hasNext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006e, code lost:
        if (r7 != r1) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0070, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0071, code lost:
        r10 = r1;
        r1 = r0;
        r0 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0080, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0082, code lost:
        r7.add(r4.next());
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0093, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0096, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009b, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009d, code lost:
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r10 = r1;
        r1 = r0;
        r0 = r10;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object toCollection(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r11, C r12, kotlin.coroutines.Continuation<? super C> r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1
            r0.<init>(r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = 0
            r12 = 0
            r2 = 0
            java.lang.Object r3 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            r4 = 0
            java.lang.Object r5 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r6 = r13.L$0
            java.util.Collection r6 = (java.util.Collection) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0048 }
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x007a
        L_0x0048:
            r1 = move-exception
            goto L_0x00a8
        L_0x004b:
            kotlin.ResultKt.throwOnFailure(r0)
            r6 = r12
            r12 = 0
            r5 = r11
            r11 = 0
            r4 = 0
            r2 = r5
            r3 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r2.iterator()     // Catch:{ all -> 0x00a4 }
            r2 = r3
            r3 = r7
            r10 = r12
            r12 = r11
            r11 = r10
        L_0x0061:
            r13.L$0 = r6     // Catch:{ all -> 0x0048 }
            r13.L$1 = r5     // Catch:{ all -> 0x0048 }
            r13.L$2 = r3     // Catch:{ all -> 0x0048 }
            r7 = 1
            r13.label = r7     // Catch:{ all -> 0x0048 }
            java.lang.Object r7 = r3.hasNext(r13)     // Catch:{ all -> 0x0048 }
            if (r7 != r1) goto L_0x0071
            return r1
        L_0x0071:
            r10 = r1
            r1 = r0
            r0 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r10
        L_0x007a:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x009c }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x009c }
            if (r0 == 0) goto L_0x0093
            java.lang.Object r0 = r4.next()     // Catch:{ all -> 0x009c }
            r8 = r0
            r9 = 0
            r7.add(r8)     // Catch:{ all -> 0x009c }
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            goto L_0x0061
        L_0x0093:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x009c }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5)
            return r7
        L_0x009c:
            r0 = move-exception
            r4 = r5
            r5 = r6
            r6 = r7
            r10 = r1
            r1 = r0
            r0 = r10
            goto L_0x00a8
        L_0x00a4:
            r1 = move-exception
            r10 = r12
            r12 = r11
            r11 = r10
        L_0x00a8:
            r2 = r1
            throw r1     // Catch:{ all -> 0x00ab }
        L_0x00ab:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r2)
            goto L_0x00b1
        L_0x00b0:
            throw r1
        L_0x00b1:
            goto L_0x00b0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toCollection(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r15.L$0 = r6;
        r15.L$1 = r5;
        r15.L$2 = r3;
        r15.label = 1;
        r7 = r3.hasNext(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006e, code lost:
        if (r7 != r1) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0070, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0071, code lost:
        r12 = r1;
        r1 = r0;
        r0 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0080, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0082, code lost:
        r8 = (kotlin.Pair) r4.next();
        r7.put(r8.getFirst(), r8.getSecond());
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009d, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a0, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a5, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a6, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a7, code lost:
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r12 = r1;
        r1 = r0;
        r0 = r12;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <K, V, M extends java.util.Map<? super K, ? super V>> java.lang.Object toMap(kotlinx.coroutines.channels.ReceiveChannel<? extends kotlin.Pair<? extends K, ? extends V>> r13, M r14, kotlin.coroutines.Continuation<? super M> r15) {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r15 = r0.label
            int r15 = r15 - r2
            r0.label = r15
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2
            r0.<init>(r15)
        L_0x0019:
            r15 = r0
            java.lang.Object r0 = r15.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r15.label
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x002d:
            r13 = 0
            r14 = 0
            r2 = 0
            java.lang.Object r3 = r15.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            r4 = 0
            java.lang.Object r5 = r15.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r6 = r15.L$0
            java.util.Map r6 = (java.util.Map) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0048 }
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x007a
        L_0x0048:
            r1 = move-exception
            goto L_0x00b2
        L_0x004b:
            kotlin.ResultKt.throwOnFailure(r0)
            r6 = r14
            r14 = 0
            r5 = r13
            r13 = 0
            r4 = 0
            r2 = r5
            r3 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r2.iterator()     // Catch:{ all -> 0x00ae }
            r2 = r3
            r3 = r7
            r12 = r14
            r14 = r13
            r13 = r12
        L_0x0061:
            r15.L$0 = r6     // Catch:{ all -> 0x0048 }
            r15.L$1 = r5     // Catch:{ all -> 0x0048 }
            r15.L$2 = r3     // Catch:{ all -> 0x0048 }
            r7 = 1
            r15.label = r7     // Catch:{ all -> 0x0048 }
            java.lang.Object r7 = r3.hasNext(r15)     // Catch:{ all -> 0x0048 }
            if (r7 != r1) goto L_0x0071
            return r1
        L_0x0071:
            r12 = r1
            r1 = r0
            r0 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r12
        L_0x007a:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00a6 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00a6 }
            if (r0 == 0) goto L_0x009d
            java.lang.Object r0 = r4.next()     // Catch:{ all -> 0x00a6 }
            r8 = r0
            kotlin.Pair r8 = (kotlin.Pair) r8     // Catch:{ all -> 0x00a6 }
            r9 = 0
            java.lang.Object r10 = r8.getFirst()     // Catch:{ all -> 0x00a6 }
            java.lang.Object r11 = r8.getSecond()     // Catch:{ all -> 0x00a6 }
            r7.put(r10, r11)     // Catch:{ all -> 0x00a6 }
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            goto L_0x0061
        L_0x009d:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00a6 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r5)
            return r7
        L_0x00a6:
            r0 = move-exception
            r4 = r5
            r5 = r6
            r6 = r7
            r12 = r1
            r1 = r0
            r0 = r12
            goto L_0x00b2
        L_0x00ae:
            r1 = move-exception
            r12 = r14
            r14 = r13
            r13 = r12
        L_0x00b2:
            r2 = r1
            throw r1     // Catch:{ all -> 0x00b5 }
        L_0x00b5:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r2)
            goto L_0x00bb
        L_0x00ba:
            throw r1
        L_0x00bb:
            goto L_0x00ba
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toMap(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ ReceiveChannel flatMap$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$flatMap$1(receiveChannel, function2, (Continuation<? super ChannelsKt__DeprecatedKt$flatMap$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel map$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.map(receiveChannel, coroutineContext, function2);
    }

    public static final <E, R> ReceiveChannel<R> map(ReceiveChannel<? extends E> $this$map, CoroutineContext context, Function2<? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumes($this$map), new ChannelsKt__DeprecatedKt$map$1($this$map, transform, (Continuation<? super ChannelsKt__DeprecatedKt$map$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel mapIndexed$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3);
    }

    public static final <E, R> ReceiveChannel<R> mapIndexed(ReceiveChannel<? extends E> $this$mapIndexed, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumes($this$mapIndexed), new ChannelsKt__DeprecatedKt$mapIndexed$1($this$mapIndexed, transform, (Continuation<? super ChannelsKt__DeprecatedKt$mapIndexed$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel mapIndexedNotNull$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filterNotNull(ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3));
    }

    public static /* synthetic */ ReceiveChannel mapNotNull$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filterNotNull(ChannelsKt.map(receiveChannel, coroutineContext, function2));
    }

    public static /* synthetic */ ReceiveChannel withIndex$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$withIndex$1(receiveChannel, (Continuation<? super ChannelsKt__DeprecatedKt$withIndex$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel distinctBy$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.distinctBy(receiveChannel, coroutineContext, function2);
    }

    public static final <E, K> ReceiveChannel<E> distinctBy(ReceiveChannel<? extends E> $this$distinctBy, CoroutineContext context, Function2<? super E, ? super Continuation<? super K>, ? extends Object> selector) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumes($this$distinctBy), new ChannelsKt__DeprecatedKt$distinctBy$1($this$distinctBy, selector, (Continuation<? super ChannelsKt__DeprecatedKt$distinctBy$1>) null), 6, (Object) null);
    }

    public static final <E> Object toMutableSet(ReceiveChannel<? extends E> $this$toMutableSet, Continuation<? super Set<E>> $completion) {
        return ChannelsKt.toCollection($this$toMutableSet, new LinkedHashSet(), $completion);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0055, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0058, code lost:
        return r6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object any(kotlinx.coroutines.channels.ReceiveChannel r8, kotlin.coroutines.Continuation r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1
            r0.<init>(r9)
        L_0x0019:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002d:
            r8 = 0
            r1 = 0
            java.lang.Object r2 = r9.L$0
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            r3 = 0
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0039 }
            r6 = r0
            goto L_0x0055
        L_0x0039:
            r1 = move-exception
            goto L_0x005a
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r8
            r8 = 0
            r3 = 0
            r4 = r2
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r6 = r4.iterator()     // Catch:{ all -> 0x0059 }
            r9.L$0 = r2     // Catch:{ all -> 0x0059 }
            r7 = 1
            r9.label = r7     // Catch:{ all -> 0x0059 }
            java.lang.Object r6 = r6.hasNext(r9)     // Catch:{ all -> 0x0059 }
            if (r6 != r1) goto L_0x0055
            return r1
        L_0x0055:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            return r6
        L_0x0059:
            r1 = move-exception
        L_0x005a:
            r3 = r1
            throw r1     // Catch:{ all -> 0x005d }
        L_0x005d:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.any(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r14.L$0 = r8;
        r14.L$1 = r7;
        r14.L$2 = r5;
        r14.label = 1;
        r9 = r5.hasNext(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0074, code lost:
        if (r9 != r1) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0076, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0077, code lost:
        r12 = r1;
        r1 = r0;
        r0 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r2;
        r2 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0087, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0089, code lost:
        r0 = r6.next();
        r9.element++;
        r0 = r1;
        r1 = r2;
        r2 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009c, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009f, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00aa, code lost:
        return kotlin.coroutines.jvm.internal.Boxing.boxInt(r9.element);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ab, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ac, code lost:
        r2 = r4;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r12 = r1;
        r1 = r0;
        r0 = r12;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object count(kotlinx.coroutines.channels.ReceiveChannel r13, kotlin.coroutines.Continuation r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r14 = r0.label
            int r14 = r14 - r2
            r0.label = r14
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1
            r0.<init>(r14)
        L_0x0019:
            r14 = r0
            java.lang.Object r0 = r14.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r14.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x004d;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x002e:
            r13 = 0
            r2 = 0
            r4 = 0
            java.lang.Object r5 = r14.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r6 = 0
            java.lang.Object r7 = r14.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r14.L$0
            kotlin.jvm.internal.Ref$IntRef r8 = (kotlin.jvm.internal.Ref.IntRef) r8
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x004a }
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r2
            r2 = r1
            r1 = r0
            goto L_0x0081
        L_0x004a:
            r1 = move-exception
            goto L_0x00b8
        L_0x004d:
            kotlin.ResultKt.throwOnFailure(r0)
            kotlin.jvm.internal.Ref$IntRef r2 = new kotlin.jvm.internal.Ref$IntRef
            r2.<init>()
            r8 = r2
            r2 = 0
            r7 = r13
            r13 = 0
            r6 = 0
            r4 = r7
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r4.iterator()     // Catch:{ all -> 0x00b4 }
            r4 = r5
            r5 = r9
            r12 = r2
            r2 = r13
            r13 = r12
        L_0x0068:
            r14.L$0 = r8     // Catch:{ all -> 0x004a }
            r14.L$1 = r7     // Catch:{ all -> 0x004a }
            r14.L$2 = r5     // Catch:{ all -> 0x004a }
            r14.label = r3     // Catch:{ all -> 0x004a }
            java.lang.Object r9 = r5.hasNext(r14)     // Catch:{ all -> 0x004a }
            if (r9 != r1) goto L_0x0077
            return r1
        L_0x0077:
            r12 = r1
            r1 = r0
            r0 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r2
            r2 = r12
        L_0x0081:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00ab }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00ab }
            if (r0 == 0) goto L_0x009c
            java.lang.Object r0 = r6.next()     // Catch:{ all -> 0x00ab }
            r10 = 0
            int r11 = r9.element     // Catch:{ all -> 0x00ab }
            int r11 = r11 + r3
            r9.element = r11     // Catch:{ all -> 0x00ab }
            r0 = r1
            r1 = r2
            r2 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            goto L_0x0068
        L_0x009c:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ab }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r7)
            int r13 = r9.element
            java.lang.Integer r13 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r13)
            return r13
        L_0x00ab:
            r0 = move-exception
            r2 = r4
            r6 = r7
            r7 = r8
            r8 = r9
            r12 = r1
            r1 = r0
            r0 = r12
            goto L_0x00b8
        L_0x00b4:
            r1 = move-exception
            r12 = r2
            r2 = r13
            r13 = r12
        L_0x00b8:
            r3 = r1
            throw r1     // Catch:{ all -> 0x00bb }
        L_0x00bb:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            goto L_0x00c1
        L_0x00c0:
            throw r1
        L_0x00c1:
            goto L_0x00c0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.count(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: java.util.Comparator} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0094, code lost:
        if (((java.lang.Boolean) r7).booleanValue() != false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0096, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0099, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009a, code lost:
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009f, code lost:
        r9 = r3;
        r3 = r2;
        r2 = r4.next();
        r5 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r13.L$0 = r6;
        r13.L$1 = r5;
        r13.L$2 = r4;
        r13.L$3 = r2;
        r13.label = 2;
        r7 = r4.hasNext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b2, code lost:
        if (r7 != r1) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b4, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b5, code lost:
        r9 = r1;
        r1 = r0;
        r0 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c4, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c6, code lost:
        r0 = r5.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ce, code lost:
        if (r7.compare(r3, r0) >= 0) goto L_0x00db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00d0, code lost:
        r3 = r6;
        r6 = r7;
        r9 = r2;
        r2 = r0;
        r0 = r1;
        r1 = r9;
        r10 = r5;
        r5 = r3;
        r3 = r4;
        r4 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00db, code lost:
        r0 = r6;
        r6 = r7;
        r9 = r5;
        r5 = r0;
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e5, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e9, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ea, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00eb, code lost:
        r0 = r6;
        r2 = r4;
        r6 = r7;
        r9 = r1;
        r1 = r0;
        r0 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f2, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00f3, code lost:
        r2 = r3;
        r1 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f6, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f7, code lost:
        r1 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00f9, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fa, code lost:
        r1 = r5;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object maxWith(kotlinx.coroutines.channels.ReceiveChannel r11, java.util.Comparator r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1
            r0.<init>(r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x006b;
                case 1: goto L_0x004f;
                case 2: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002e:
            r11 = 0
            r12 = 0
            java.lang.Object r2 = r13.L$3
            java.lang.Object r4 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r6 = r13.L$0
            java.util.Comparator r6 = (java.util.Comparator) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x004a }
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x00be
        L_0x004a:
            r11 = move-exception
            r1 = r5
            r2 = r3
            goto L_0x0100
        L_0x004f:
            r12 = 0
            r11 = 0
            java.lang.Object r2 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r4 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r5 = r13.L$0
            r6 = r5
            java.util.Comparator r6 = (java.util.Comparator) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0066 }
            r7 = r0
            r5 = r4
            r4 = r2
            r2 = r3
            goto L_0x008e
        L_0x0066:
            r11 = move-exception
            r1 = r4
            r2 = r3
            goto L_0x0100
        L_0x006b:
            kotlin.ResultKt.throwOnFailure(r0)
            r6 = r12
            r12 = 0
            r2 = 0
            r4 = r11
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r4.iterator()     // Catch:{ all -> 0x00fc }
            r4 = r7
            r13.L$0 = r6     // Catch:{ all -> 0x00fc }
            r13.L$1 = r11     // Catch:{ all -> 0x00fc }
            r13.L$2 = r4     // Catch:{ all -> 0x00fc }
            r7 = 1
            r13.label = r7     // Catch:{ all -> 0x00fc }
            java.lang.Object r7 = r4.hasNext(r13)     // Catch:{ all -> 0x00fc }
            if (r7 != r1) goto L_0x008b
            return r1
        L_0x008b:
            r9 = r5
            r5 = r11
            r11 = r9
        L_0x008e:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x00f9 }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x00f9 }
            if (r7 != 0) goto L_0x009a
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r2)
            return r3
        L_0x009a:
            r3 = r5
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x00f6 }
            r9 = r3
            r3 = r2
            r2 = r5
            r5 = r9
        L_0x00a3:
            r13.L$0 = r6     // Catch:{ all -> 0x00f2 }
            r13.L$1 = r5     // Catch:{ all -> 0x00f2 }
            r13.L$2 = r4     // Catch:{ all -> 0x00f2 }
            r13.L$3 = r2     // Catch:{ all -> 0x00f2 }
            r7 = 2
            r13.label = r7     // Catch:{ all -> 0x00f2 }
            java.lang.Object r7 = r4.hasNext(r13)     // Catch:{ all -> 0x00f2 }
            if (r7 != r1) goto L_0x00b5
            return r1
        L_0x00b5:
            r9 = r1
            r1 = r0
            r0 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r9
        L_0x00be:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00ea }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00ea }
            if (r0 == 0) goto L_0x00e5
            java.lang.Object r0 = r5.next()     // Catch:{ all -> 0x00ea }
            int r8 = r7.compare(r3, r0)     // Catch:{ all -> 0x00ea }
            if (r8 >= 0) goto L_0x00db
            r3 = r6
            r6 = r7
            r9 = r2
            r2 = r0
            r0 = r1
            r1 = r9
            r10 = r5
            r5 = r3
            r3 = r4
            r4 = r10
            goto L_0x00a3
        L_0x00db:
            r0 = r6
            r6 = r7
            r9 = r5
            r5 = r0
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r9
            goto L_0x00a3
        L_0x00e5:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4)
            return r3
        L_0x00ea:
            r11 = move-exception
            r0 = r6
            r2 = r4
            r6 = r7
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x0100
        L_0x00f2:
            r11 = move-exception
            r2 = r3
            r1 = r5
            goto L_0x0100
        L_0x00f6:
            r11 = move-exception
            r1 = r3
            goto L_0x0100
        L_0x00f9:
            r11 = move-exception
            r1 = r5
            goto L_0x0100
        L_0x00fc:
            r1 = move-exception
            r9 = r1
            r1 = r11
            r11 = r9
        L_0x0100:
            r2 = r11
            throw r11     // Catch:{ all -> 0x0103 }
        L_0x0103:
            r11 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r2)
            goto L_0x0109
        L_0x0108:
            throw r11
        L_0x0109:
            goto L_0x0108
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.maxWith(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: java.util.Comparator} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0094, code lost:
        if (((java.lang.Boolean) r7).booleanValue() != false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0096, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0099, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009a, code lost:
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009f, code lost:
        r9 = r3;
        r3 = r2;
        r2 = r4.next();
        r5 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r13.L$0 = r6;
        r13.L$1 = r5;
        r13.L$2 = r4;
        r13.L$3 = r2;
        r13.label = 2;
        r7 = r4.hasNext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b2, code lost:
        if (r7 != r1) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b4, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b5, code lost:
        r9 = r1;
        r1 = r0;
        r0 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c4, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c6, code lost:
        r0 = r5.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ce, code lost:
        if (r7.compare(r3, r0) <= 0) goto L_0x00db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00d0, code lost:
        r3 = r6;
        r6 = r7;
        r9 = r2;
        r2 = r0;
        r0 = r1;
        r1 = r9;
        r10 = r5;
        r5 = r3;
        r3 = r4;
        r4 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00db, code lost:
        r0 = r6;
        r6 = r7;
        r9 = r5;
        r5 = r0;
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e5, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e9, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ea, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00eb, code lost:
        r0 = r6;
        r2 = r4;
        r6 = r7;
        r9 = r1;
        r1 = r0;
        r0 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f2, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00f3, code lost:
        r2 = r3;
        r1 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f6, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f7, code lost:
        r1 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00f9, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fa, code lost:
        r1 = r5;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object minWith(kotlinx.coroutines.channels.ReceiveChannel r11, java.util.Comparator r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1
            r0.<init>(r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x006b;
                case 1: goto L_0x004f;
                case 2: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002e:
            r11 = 0
            r12 = 0
            java.lang.Object r2 = r13.L$3
            java.lang.Object r4 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r6 = r13.L$0
            java.util.Comparator r6 = (java.util.Comparator) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x004a }
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x00be
        L_0x004a:
            r11 = move-exception
            r1 = r5
            r2 = r3
            goto L_0x0100
        L_0x004f:
            r12 = 0
            r11 = 0
            java.lang.Object r2 = r13.L$2
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r4 = r13.L$1
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r5 = r13.L$0
            r6 = r5
            java.util.Comparator r6 = (java.util.Comparator) r6
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0066 }
            r7 = r0
            r5 = r4
            r4 = r2
            r2 = r3
            goto L_0x008e
        L_0x0066:
            r11 = move-exception
            r1 = r4
            r2 = r3
            goto L_0x0100
        L_0x006b:
            kotlin.ResultKt.throwOnFailure(r0)
            r6 = r12
            r12 = 0
            r2 = 0
            r4 = r11
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r4.iterator()     // Catch:{ all -> 0x00fc }
            r4 = r7
            r13.L$0 = r6     // Catch:{ all -> 0x00fc }
            r13.L$1 = r11     // Catch:{ all -> 0x00fc }
            r13.L$2 = r4     // Catch:{ all -> 0x00fc }
            r7 = 1
            r13.label = r7     // Catch:{ all -> 0x00fc }
            java.lang.Object r7 = r4.hasNext(r13)     // Catch:{ all -> 0x00fc }
            if (r7 != r1) goto L_0x008b
            return r1
        L_0x008b:
            r9 = r5
            r5 = r11
            r11 = r9
        L_0x008e:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x00f9 }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x00f9 }
            if (r7 != 0) goto L_0x009a
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r2)
            return r3
        L_0x009a:
            r3 = r5
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x00f6 }
            r9 = r3
            r3 = r2
            r2 = r5
            r5 = r9
        L_0x00a3:
            r13.L$0 = r6     // Catch:{ all -> 0x00f2 }
            r13.L$1 = r5     // Catch:{ all -> 0x00f2 }
            r13.L$2 = r4     // Catch:{ all -> 0x00f2 }
            r13.L$3 = r2     // Catch:{ all -> 0x00f2 }
            r7 = 2
            r13.label = r7     // Catch:{ all -> 0x00f2 }
            java.lang.Object r7 = r4.hasNext(r13)     // Catch:{ all -> 0x00f2 }
            if (r7 != r1) goto L_0x00b5
            return r1
        L_0x00b5:
            r9 = r1
            r1 = r0
            r0 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r9
        L_0x00be:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00ea }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00ea }
            if (r0 == 0) goto L_0x00e5
            java.lang.Object r0 = r5.next()     // Catch:{ all -> 0x00ea }
            int r8 = r7.compare(r3, r0)     // Catch:{ all -> 0x00ea }
            if (r8 <= 0) goto L_0x00db
            r3 = r6
            r6 = r7
            r9 = r2
            r2 = r0
            r0 = r1
            r1 = r9
            r10 = r5
            r5 = r3
            r3 = r4
            r4 = r10
            goto L_0x00a3
        L_0x00db:
            r0 = r6
            r6 = r7
            r9 = r5
            r5 = r0
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r9
            goto L_0x00a3
        L_0x00e5:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r4)
            return r3
        L_0x00ea:
            r11 = move-exception
            r0 = r6
            r2 = r4
            r6 = r7
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x0100
        L_0x00f2:
            r11 = move-exception
            r2 = r3
            r1 = r5
            goto L_0x0100
        L_0x00f6:
            r11 = move-exception
            r1 = r3
            goto L_0x0100
        L_0x00f9:
            r11 = move-exception
            r1 = r5
            goto L_0x0100
        L_0x00fc:
            r1 = move-exception
            r9 = r1
            r1 = r11
            r11 = r9
        L_0x0100:
            r2 = r11
            throw r11     // Catch:{ all -> 0x0103 }
        L_0x0103:
            r11 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r2)
            goto L_0x0109
        L_0x0108:
            throw r11
        L_0x0109:
            goto L_0x0108
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.minWith(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
        if (((java.lang.Boolean) r7).booleanValue() != false) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0060, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0061, code lost:
        r3 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0065, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0068, code lost:
        return r3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object none(kotlinx.coroutines.channels.ReceiveChannel r8, kotlin.coroutines.Continuation r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1
            r0.<init>(r9)
        L_0x0019:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x003d;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002e:
            r8 = 0
            r1 = 0
            java.lang.Object r2 = r9.L$0
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            r4 = 0
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x003a }
            r7 = r0
            goto L_0x0057
        L_0x003a:
            r1 = move-exception
            r3 = r4
            goto L_0x006b
        L_0x003d:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r8
            r8 = 0
            r4 = 0
            r5 = r2
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch:{ all -> 0x0069 }
            r9.L$0 = r2     // Catch:{ all -> 0x0069 }
            r9.label = r3     // Catch:{ all -> 0x0069 }
            java.lang.Object r7 = r7.hasNext(r9)     // Catch:{ all -> 0x0069 }
            if (r7 != r1) goto L_0x0056
            return r1
        L_0x0056:
            r1 = r6
        L_0x0057:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x003a }
            boolean r5 = r7.booleanValue()     // Catch:{ all -> 0x003a }
            if (r5 != 0) goto L_0x0060
            goto L_0x0061
        L_0x0060:
            r3 = 0
        L_0x0061:
            java.lang.Boolean r3 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)     // Catch:{ all -> 0x003a }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r4)
            return r3
        L_0x0069:
            r1 = move-exception
            r3 = r4
        L_0x006b:
            r3 = r1
            throw r1     // Catch:{ all -> 0x006e }
        L_0x006e:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.none(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ ReceiveChannel zip$default(ReceiveChannel receiveChannel, ReceiveChannel receiveChannel2, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.zip(receiveChannel, receiveChannel2, coroutineContext, function2);
    }

    public static final <E, R, V> ReceiveChannel<V> zip(ReceiveChannel<? extends E> $this$zip, ReceiveChannel<? extends R> other, CoroutineContext context, Function2<? super E, ? super R, ? extends V> transform) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumesAll($this$zip, other), new ChannelsKt__DeprecatedKt$zip$2(other, $this$zip, transform, (Continuation<? super ChannelsKt__DeprecatedKt$zip$2>) null), 6, (Object) null);
    }

    public static final Function1<Throwable, Unit> consumes(ReceiveChannel<?> $this$consumes) {
        return new ChannelsKt__DeprecatedKt$consumes$1($this$consumes);
    }
}

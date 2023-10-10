package tt.hashtranslator.decoder;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import lombok.SneakyThrows;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.MimeType;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Component
public class JsonProtobufDecoder extends ProtobufDecoder {
    private static final ConcurrentMap<Class<?>, Method> methodCache = new ConcurrentReferenceHashMap<>();

    private static final MimeType APPLICATION_JSON = MimeType.valueOf("application/json");

    @Override
    protected boolean supportsMimeType(MimeType mimeType) {
        return APPLICATION_JSON.equals(mimeType) || super.supportsMimeType(mimeType);
    }

    @SneakyThrows
    @Override
    public Message decode(DataBuffer dataBuffer, ResolvableType targetType, MimeType mimeType, Map<String, Object> hints) throws DecodingException {
        if (!APPLICATION_JSON.equals(mimeType)) {
            return super.decode(dataBuffer, targetType, mimeType, hints);
        }
        Message.Builder builder = getMessageBuilder(targetType.toClass());
        String json = dataBuffer.toString(Charset.defaultCharset());
        JsonFormat.parser().ignoringUnknownFields().merge(json, builder);

        return builder.build();
    }

    private static Message.Builder getMessageBuilder(Class<?> clazz) throws Exception {
        Method method = methodCache.get(clazz);
        if (method == null) {
            method = clazz.getMethod("newBuilder");
            methodCache.put(clazz, method);
        }
        return (Message.Builder) method.invoke(clazz);
    }
}

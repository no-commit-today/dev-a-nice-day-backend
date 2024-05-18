package com.nocommittoday.techswipe.batch.job;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.batch.item.support.AbstractFileItemWriter;
import org.springframework.core.io.WritableResource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.Iterator;
import java.util.List;

public class JsonFileListItemWriter<T> extends AbstractFileItemWriter<List<T>> {

    private static final char JSON_OBJECT_SEPARATOR = ',';

    private static final char JSON_ARRAY_START = '[';

    private static final char JSON_ARRAY_STOP = ']';

    private JsonObjectMarshaller<T> jsonObjectMarshaller;

    public JsonFileListItemWriter(
            final WritableResource resource,
            final JsonObjectMarshaller<T> jsonObjectMarshaller
    ) {
        Assert.notNull(resource, "resource must not be null");
        Assert.notNull(jsonObjectMarshaller, "json object marshaller must not be null");
        setResource(resource);
        setJsonObjectMarshaller(jsonObjectMarshaller);
        setHeaderCallback(writer -> writer.write(JSON_ARRAY_START));
        setFooterCallback(writer -> writer.write(this.lineSeparator + JSON_ARRAY_STOP + this.lineSeparator));
        setExecutionContextName(ClassUtils.getShortName(JsonFileItemWriter.class));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.append) {
            this.shouldDeleteIfExists = false;
        }
    }

    public void setJsonObjectMarshaller(JsonObjectMarshaller<T> jsonObjectMarshaller) {
        this.jsonObjectMarshaller = jsonObjectMarshaller;
    }

    @Override
    public String doWrite(final Chunk<? extends List<T>> items) {
        final StringBuilder lines = new StringBuilder();
        final Iterator<? extends List<T>> listIterator = items.iterator();
        if (!items.isEmpty() && state.getLinesWritten() > 0) {
            lines.append(JSON_OBJECT_SEPARATOR).append(this.lineSeparator);
        }
        while (listIterator.hasNext()) {
            final List<T> list = listIterator.next();
            final Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                final T item = iterator.next();
                lines.append(' ').append(this.jsonObjectMarshaller.marshal(item));
                if (iterator.hasNext()) {
                    lines.append(JSON_OBJECT_SEPARATOR).append(this.lineSeparator);
                }
            }
        }
        return lines.toString();

    }
}

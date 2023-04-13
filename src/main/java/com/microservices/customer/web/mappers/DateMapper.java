package com.microservices.customer.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {
    public OffsetDateTime asOffsetDateTime(Timestamp ts){
        if (ts != null){
            OffsetDateTime offset = OffsetDateTime.of(ts.toLocalDateTime(), ZoneOffset.UTC);
            return offset;
        }else{
            return null;
        }
    }
    public Timestamp asTimestamp(OffsetDateTime offsetDateTime){
        if (offsetDateTime != null){
            Timestamp stamp = Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
            return stamp;
        }else{
            return null;
        }
    }

}

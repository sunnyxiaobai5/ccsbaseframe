package cn.ccsgroup.ccsframework.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import cn.ccsgroup.ccsframework.components.AppConst;

public class JsonDateSerializer extends JsonSerializer<Date>
{

  @Override
  public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException
  {
    SimpleDateFormat formatter = new SimpleDateFormat(AppConst.DATE_PATTEN);  
    String formattedDate = formatter.format(arg0);  
    arg1.writeString(formattedDate);  
  }
  
}

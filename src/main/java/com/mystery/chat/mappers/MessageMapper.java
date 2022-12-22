package com.mystery.chat.mappers;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author shouchen
 * @date 2022/12/21
 */
@Mapper
public interface MessageMapper {

    int getMaxID();
}

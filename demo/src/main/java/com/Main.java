package com;

import com.entity.MyEntityDTO;
import com.entity.MyEntityDTOEntityConvert;
import com.entity.MyEntityVO;

public class Main {
    public static void main(String[] args) {
        MyEntityDTO dto = new MyEntityDTO();
        dto.setA("this is a");
        dto.setB("这是b");
        MyEntityVO vo = MyEntityDTOEntityConvert.INSTANCE.dtoToVo(dto);
        System.out.println(vo);
    }
}

package com.wegrow.wegrow.demo.service.imlp;

import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.demo.dto.BlockDto;
import com.wegrow.wegrow.demo.service.DemoService;
import com.wegrow.wegrow.mapper.BlockMapper;
import com.wegrow.wegrow.model.Block;
import com.wegrow.wegrow.model.BlockExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private BlockMapper blockMapper;

    @Override
    public List<Block> listAllBlock() {
        return blockMapper.selectByExample(new BlockExample());
    }

    @Override
    public int createBlock(BlockDto blockDto) {
        Block block1 = new Block();
        BeanUtils.copyProperties(blockDto, block1);
        return blockMapper.insertSelective(block1);
    }

    @Override
    public int updateBlock(Integer id, BlockDto blockDto) {
        Block block = new Block();
        BeanUtils.copyProperties(blockDto, block);
        block.setId(id);
        return blockMapper.updateByPrimaryKeySelective(block);
    }

    @Override
    public int deleteBlock(Integer id) {
        return blockMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Block> listBlock(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return blockMapper.selectByExample(new BlockExample());
    }

    @Override
    public Block getBlock(Integer id) {
        return blockMapper.selectByPrimaryKey(id);
    }
}

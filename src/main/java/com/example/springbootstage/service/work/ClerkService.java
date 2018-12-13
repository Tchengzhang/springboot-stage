package com.example.springbootstage.service.work;

import com.example.springbootstage.dao.work.ClerkDao;
import com.example.springbootstage.entity.work.Clerk;
import com.example.springbootstage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Service
public class ClerkService implements BaseService<Clerk> {
    
    @Autowired
    private ClerkDao clerkDao;

    @Override
    public Clerk save(Clerk clerk) {
        return clerkDao.save(clerk);
    }

    @Override
    public Clerk getById(Long id) {
        Optional<Clerk> optional = clerkDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Clerk> getAll() {
        Iterable<Clerk> it = clerkDao.findAll();
        List<Clerk> clerkList = new LinkedList<>();
        it.forEach(clerkList::add);
        return clerkList;
    }

    @Override
    public void delById(Long id) {
        clerkDao.deleteById(id);
    }
}

package com.asynclogger.asynclogger.service.impl;

import com.asynclogger.asynclogger.entity.Log;
import com.asynclogger.asynclogger.repository.LogRepository;
import com.asynclogger.asynclogger.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements ILogService {

  private final LogRepository logRepository;

  @Autowired
  public LogServiceImpl(LogRepository logRepository) {
    this.logRepository = logRepository;
  }

  @Override
  public Log saveLog(Log log) {
    return logRepository.save(log);
  }

  @Override
  public Log findById(Long id) {
    return logRepository.findById(id).orElse(null);
  }

  @Override
  public List<Log> findAll() {
    return logRepository.findAll();
  }

  @Override
  public Log updateLog(Long id, Log log) {
    log.setId(id);
    return logRepository.save(log);
  }

  @Override
  public Log deleteLog(Long id) {
    Log log = findById(id);
    logRepository.deleteById(id);
    return log;
  }
}

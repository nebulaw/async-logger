package com.asynclogger.asynclogger.service;

import com.asynclogger.asynclogger.entity.Log;

import java.util.List;

public interface ILogService {

  Log saveLog(Log log);

  Log findById(Long id);

  List<Log> findAll();

  Log updateLog(Long id, Log log);

  Log deleteLog(Long id);

}

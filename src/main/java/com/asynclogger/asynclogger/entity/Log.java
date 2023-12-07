package com.asynclogger.asynclogger.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Table(name = "LOGS")
@Component
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Log {
  @Id
  @Column
  @SequenceGenerator(name = "logSequence", sequenceName = "LOG_SEQUENCE", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logSequence")
  private Long id;

  @Column(name = "METHOD_NAME")
  private String methodName;

  @Column(name = "CLASS_NAME")
  private String className;

  @Column(name = "METHOD_ARGS")
  private String args;

  @Column(name = "METHOD_EXECUTION_TIME")
  private Double executionTime;

  public Log(String methodName, String className, String args, Double executionTime) {
    this.methodName = methodName;
    this.className = className;
    this.args = args;
    this.executionTime = executionTime;
  }

}

# Project

Project Description

<em>[TODO.md spec & Kanban Board](https://bit.ly/3fCwKfM)</em>

### Todo

- [ ] Flow Calculation Engine
  - Implement flow rate calculations based on pipe properties
  - Consider pressure drops and network topology
  - Handle multiple connected nodes

- [ ] Network Analysis
  - Path finding between sources and customers
  - Network capacity optimization
  - Bottleneck detection

- [ ] Time-based Simulation
  - Implement simulation steps/ticks
  - Handle dynamic demand changes
  - Track flow states over time
  - - [ ] State Management
  - Current network state tracking
  - Historical data recording
  - Performance metrics collection

- [ ] Persistence Layer
  - Network configuration storage
  - Simulation results storage
  - Data export/import capabilities
  - - [ ] Network Visualization
  - Graph visualization using GraphStream
  - Geographic visualization with JXMapViewer
  - Real-time flow animation

- [ ] User Interface
  - Network editor
  - Simulation controls
  - Results dashboard
- [ ] Simulation Tests
  - Flow calculation validation
  - Network behavior verification
  - Performance benchmarking

- [ ] Integration Tests
  - Full system testing
  - Edge case handling
  - Load testing

  - # Water Distribution Network Development Plan

## 1. Simulation Core 🚰

### Flow Calculation Engine

- [ ] Define Flow Calculation interfaces
- [ ] Implement pipe flow calculations
  - [ ] Pressure drop calculations
  - [ ] Flow rate determination
  - [ ] Friction loss modeling
- [ ] Handle network topology analysis
  - [ ] Multi-node flow distribution
  - [ ] Pressure balancing
- [ ] Implement result models

### Network Analysis

- [ ] Path finding algorithm implementation
  - [ ] Shortest path calculation
  - [ ] Flow capacity consideration
- [ ] Network optimization
  - [ ] Capacity utilization
  - [ ] Flow distribution balance
- [ ] Bottleneck detection system

### Time-based Simulation

- [ ] Simulation step implementation
- [ ] Demand change handling
- [ ] Flow state tracking
- [ ] Event system for state changes

## 2. Data Management 📊

### State Management

- [ ] Network state tracker
- [ ] Historical data recorder
- [ ] Performance metrics system

### Persistence Layer

- [ ] Configuration storage implementation
- [ ] Results storage system
- [ ] Data import/export functionality

## 3. Visualization & UI 🖥️

### Network Visualization

- [ ] Graph visualization implementation
- [ ] Geographic view development
- [ ] Flow animation system

### User Interface

- [ ] Network editor development
- [ ] Simulation control panel
- [ ] Results dashboard

## 4. Testing & Validation ✅

### Simulation Tests

- [ ] Flow calculation validation
- [ ] Network behavior verification
- [ ] Performance benchmarks

### Integration Tests

- [ ] System integration testing
- [ ] Edge case handling
- [ ] Load testing suite

### In Progress

- [ ] Entity Tests
  - [x] CustomerTest
  - [ ] PipeTest
  - [ ] WaterDistributionTest

### Done ✓

- [x] PipePropertiesTest
- [x] WaterDemandTest
- [x] VolumeTest
- [x] DistanceTest
- [x] CoordinateTest
- [x] Value Object Tests
- [x] Basic value objects (Distance, Volume, etc)
- [x] Core entities (Customer, Pipe, WaterSource)

#!/usr/bin/env python

import pygame
import os
import sys
from load_image import load_image

"""
Compiler

Initial functionality: Organizing graphical representation
into data structure.

Next: Make a call to ViennaRNA for comparisons

"""

def print_parts(gates):
    for gate in gates:
        print "GATE:  type: %s" %gate.type
        for inSocket in gate.inSockets:
            print "IN SOCKET: Type: %s, Gate: %s, State: %s, Index: %s"\
            %(inSocket.type, inSocket.gate.type, inSocket.state,inSocket.index)
        if gate.type == 'not' or gate.type == 'switch':
            outSocket = gate.outSocket
            print "OUT SOCKET: Type: %s, Gate: %s, State: %s, Index: %s \n"\
            %(outSocket.type, outSocket.gate.type, outSocket.state,outSocket.index)
        else: print ''

def print_circuit(gates):
    switches = []
    bulbs = []
    gates = []
    for gate in gates:
        if gate.type == 'switch':
            switches.append(gate)
    for switch in switches:
        DFS_switch(switch,gates,switches)

def DFS_switch(switch,gates):
    return 1

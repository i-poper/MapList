package net.poper.maplist

import spock.lang.Specification
import spock.lang.Unroll

class MapListTest extends Specification {
    void normal() {
    expect:
        MapList.create {
            a | b | c
            1 | 2 | 3
            x | y | z
        } == [ [a:1, b:2, c:3], [a:'x', b:'y', c:'z'] ]
    }

    void "boolean"() {
    expect:
        MapList.create {
            a | b
            true | false
        } == [ [ a:true, b:false] ]
    }

    void "integer"() {
    expect:
        MapList.create {
            a | b
            133 | 22
        } == [ [a:133, b:22] ]
    }

    void "Numeric header"() {
    expect:
        MapList.create {
            1 | 3
            a | b
        } == [ [1:'a', 3:'b'] ]
    }

    void "various  header"() {
    expect:
        MapList.create {
            1 | x 
            a | b
        } == [ [1:'a', x:'b'] ]
    }

    void "header < data"() {
    expect:
        MapList.create {
            a | b | c
            1 | 2 | 3 | 4
        } == [[ a:1, b:2, c:3]]
    }

    void "header > data"() {
    expect:
        MapList.create {
            a | b | c
            1 | 2
        } == [[ a:1, b:2 ]]
    }
}
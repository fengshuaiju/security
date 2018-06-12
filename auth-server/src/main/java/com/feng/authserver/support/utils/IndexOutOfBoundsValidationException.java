/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.feng.authserver.support.utils;

/**
 * Thrown to indicate that an index of some sort (such as to an array, to a
 * string, or to a vector) is out of range.
 * <p>
 * Applications can subclass this class to indicate similar exceptions.
 *
 * @author  Frank Yellin
 * @since   JDK1.0
 */
public
class IndexOutOfBoundsValidationException extends ValidationException {
    private static final long serialVersionUID = 234122996006267687L;

    /**
     * Constructs an <code>IndexOutOfBoundsValidationException</code> with no
     * detail message.
     */
    public IndexOutOfBoundsValidationException() {
        super();
    }

    /**
     * Constructs an <code>IndexOutOfBoundsValidationException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public IndexOutOfBoundsValidationException(String s) {
        super(s);
    }
}

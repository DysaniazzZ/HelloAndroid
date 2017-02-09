package com.example.dysaniazzz.chapter09;

import com.orhanobut.logger.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by DysaniazzZ on 09/02/2017.
 * 第九章：使用SAX方式解析XML文件
 */
public class SAXHandler extends DefaultHandler {

    private String mNodeName;
    private StringBuilder mId;
    private StringBuilder mName;
    private StringBuilder mVersion;

    /**
     * 开始XML解析的时候调用
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        mId = new StringBuilder();
        mName = new StringBuilder();
        mVersion = new StringBuilder();
    }

    /**
     * 解析某个节点的时候调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //记录当前节点名
        mNodeName = localName;
    }

    /**
     * 获取节点中内容的时候调用
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //根据当前的节点名判断将内容添加到哪一个StringBuilder中
        if ("id".equals(mNodeName)) {
            mId.append(ch, start, length);
        } else if ("name".equals(mNodeName)) {
            mName.append(ch, start, length);
        } else if ("version".equals(mNodeName)) {
            mVersion.append(ch, start, length);
        }
    }

    /**
     * 完成解析某个节点的时候调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(localName)) {
            Logger.d("id is " + mId.toString().trim());
            Logger.d("name is " + mName.toString().trim());
            Logger.d("version is " + mVersion.toString().trim());
            //最后要将StringBuilder清空掉
            mId.setLength(0);
            mName.setLength(0);
            mVersion.setLength(0);
        }
    }

    /**
     * 完成整改XML解析的时候调用
     *
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.util.List;

/**
 *
 * @author Asus
 * @param <T>
 */
public interface IFileManager<T> {

    List<T> loadFile(String fileName) throws Exception;

    boolean saveFile(String fileName, List<T> list) throws Exception;
}

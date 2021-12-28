package com.placidshwift.hexgates.libs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

/*
 * Persistent HexCoreDataType
 * - Generalizes the PersistentDataType and Implements it to the HexCoreData class
 */
public class HexCoreDataType implements PersistentDataType<byte[], HexCoreData> {

	@Override
	public Class<HexCoreData> getComplexType() {
		return HexCoreData.class;
	}

	@Override
	public Class<byte[]> getPrimitiveType() {
		return byte[].class;
	}

	@Override
	public byte[] toPrimitive(HexCoreData complex, PersistentDataAdapterContext arg1) {
		ByteArrayOutputStream bte = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(bte);
			out.writeObject(complex);
			out.flush();
			return bte.toByteArray();
		} 
		catch (IOException e) {e.printStackTrace();}
		finally {
			try {
				bte.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		return null;
	}
	
	@Override
	public HexCoreData fromPrimitive(byte[] primitive, PersistentDataAdapterContext arg1) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(primitive);
			ObjectInputStream in = new ObjectInputStream(bis);
			return (HexCoreData) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}

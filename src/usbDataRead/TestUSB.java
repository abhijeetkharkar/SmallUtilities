package usbDataRead;

import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

public class TestUSB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Context context = new Context();
		int result = LibUsb.init(context);
		if (result != LibUsb.SUCCESS) {
			throw new LibUsbException("Unable to initialize libusb.", result);
		}
		else
		{
			System.out.println("Success");
		}
		
		// Read the USB device list
	    DeviceList list = new DeviceList();
	    int result1 = LibUsb.getDeviceList(null, list);
	    if (result1 < 0) {
	    	throw new LibUsbException("Unable to get device list", result1);
	    } else {
	    	System.out.println(list.getSize());
	    }

	    try
	    {
	        // Iterate over all devices and scan for the right one
	        for (Device device: list)
	        {
	            DeviceDescriptor descriptor = new DeviceDescriptor();
	            result = LibUsb.getDeviceDescriptor(device, descriptor);
	            if (result != LibUsb.SUCCESS) {
	            	throw new LibUsbException("Unable to read device descriptor", result);
	            }
	            //if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
	            System.out.println(descriptor.idVendor()+":"+descriptor.bcdUSB()+":"+descriptor.bDescriptorType()+":"+descriptor.bDeviceClass()+":"+descriptor.idProduct());
	        }
	    }
	    finally
	    {
	        // Ensure the allocated device list is freed
	        LibUsb.freeDeviceList(list, true);
	    }
		
		
		LibUsb.exit(context);
	}

}

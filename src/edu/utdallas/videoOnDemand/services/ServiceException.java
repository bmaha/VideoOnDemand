package edu.utdallas.videoOnDemand.services;

@SuppressWarnings("serial")
public class ServiceException extends Exception
{
	public ServiceException(String msg)
	{
		super(msg);
	}

	public ServiceException(String msg, Throwable ex)
	{
		super(msg, ex);
	}
}
